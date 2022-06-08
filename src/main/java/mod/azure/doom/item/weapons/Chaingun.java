package mod.azure.doom.item.weapons;

import java.util.function.Consumer;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.Keybindings;
import mod.azure.doom.client.render.weapons.ChaingunRender;
import mod.azure.doom.entity.projectiles.ChaingunBulletEntity;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.packets.weapons.ChaingunLoadingPacket;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.network.PacketDistributor;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Chaingun extends DoomBaseItem {

	public Chaingun() {
		super(new Item.Properties().tab(DoomMod.DoomWeaponItemGroup).stacksTo(1).durability(201));
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IItemRenderProperties() {
			private final BlockEntityWithoutLevelRenderer renderer = new ChaingunRender();

			@Override
			public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
				return renderer;
			}
		});
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return DoomTier.CHAINGUN.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public void onUseTick(Level worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof Player) {
			Player playerentity = (Player) entityLiving;
			if (stack.getDamageValue() < (stack.getMaxDamage() - 1)) {
				if (!playerentity.getCooldowns().isOnCooldown(this)) {
					playerentity.getCooldowns().addCooldown(this, 3);
					if (!worldIn.isClientSide) {
						ChaingunBulletEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
						abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(),
								playerentity.getYRot(), 0.0F, 1.0F * 3.0F, 1.0F);
						abstractarrowentity.isNoGravity();

						stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
						worldIn.addFreshEntity(abstractarrowentity);
						worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
								DoomSounds.CHAINGUN_SHOOT.get(), SoundSource.PLAYERS, 1.0F,
								1.0F / (worldIn.random.nextFloat() * 0.4F + 1.2F) + 0.25F * 0.5F);
						if (!worldIn.isClientSide) {
							final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerLevel) worldIn);
							final PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF
									.with(() -> playerentity);
							GeckoLibNetwork.syncAnimation(target, this, id, ANIM_OPEN);
						}
						boolean isInsideWaterBlock = playerentity.level.isWaterAt(playerentity.blockPosition());
						spawnLightSource(entityLiving, isInsideWaterBlock);
					}
				}
			} else {
				worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
						DoomSounds.EMPTY.get(), SoundSource.PLAYERS, 1.0F, 1.5F);
			}
		}
	}

	public ChaingunBulletEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		float j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		ChaingunBulletEntity arrowentity = new ChaingunBulletEntity(worldIn, shooter,
				(DoomConfig.SERVER.chaingun_bullet_damage.get().floatValue() + (j * 2.0F)));
		return arrowentity;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (worldIn.isClientSide) {
			if (((Player) entityIn).getMainHandItem().getItem() instanceof Chaingun) {
				while (Keybindings.RELOAD.consumeClick() && isSelected) {
					DoomPacketHandler.CHAINGUN.sendToServer(new ChaingunLoadingPacket(itemSlot));
				}
			}
		}
	}

	public static void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof Chaingun) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0
					&& user.getInventory().countItem(DoomItems.CHAINGUN_BULLETS.get()) > 0) {
				removeAmmo(DoomItems.CHAINGUN_BULLETS.get(), user);
				user.getItemInHand(hand).hurtAndBreak(-50, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
				user.getCommandSenderWorld().playSound((Player) null, user.getX(), user.getY(), user.getZ(),
						DoomSounds.CLIPRELOAD.get(), SoundSource.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	public static float getArrowVelocity(int charge) {
		float f = (float) charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	public ChaingunBulletEntity customeArrow(ChaingunBulletEntity arrow) {
		return arrow;
	}
}