package mod.azure.doom.item.weapons;

import java.util.List;
import java.util.function.Consumer;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.Keybindings;
import mod.azure.doom.client.render.weapons.DSGRender;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.packets.weapons.DSGLoadingPacket;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.network.PacketDistributor;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class DShotgun extends DoomBaseItem {

	public DShotgun() {
		super(new Item.Properties().tab(DoomMod.DoomWeaponItemGroup).stacksTo(1).durability(51));
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IItemRenderProperties() {
			private final BlockEntityWithoutLevelRenderer renderer = new DSGRender();

			@Override
			public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
				return renderer;
			}
		});
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return DoomTier.SHOTGUN.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof Player) {
			Player playerentity = (Player) entityLiving;
			if (stack.getDamageValue() < (stack.getMaxDamage() - 1)) {
				playerentity.getCooldowns().addCooldown(this, 10);
				if (!worldIn.isClientSide) {
					ShotgunShellEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
					abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(),
							0.0F, 1.0F * 3.0F, 1.0F);
					abstractarrowentity.isNoGravity();

					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					worldIn.addFreshEntity(abstractarrowentity);
					worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
							DoomSounds.SHOTGUN_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.5F);
					if (!worldIn.isClientSide) {
						final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerLevel) worldIn);
						final PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF
								.with(() -> playerentity);
						GeckoLibNetwork.syncAnimation(target, this, id, ANIM_OPEN);
					}
					boolean isInsideWaterBlock = playerentity.level.isWaterAt(playerentity.blockPosition());
					spawnLightSource(entityLiving, isInsideWaterBlock);
				}
			} else {
				worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
						DoomSounds.EMPTY.get(), SoundSource.PLAYERS, 1.0F, 1.5F);
			}
		}
	}

	public ShotgunShellEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		float j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		ShotgunShellEntity arrowentity = new ShotgunShellEntity(worldIn, shooter,
				(DoomConfig.SERVER.shotgun_damage.get().floatValue() + (j * 2.0F)));
		return arrowentity;
	}

	@Override
	public void inventoryTick(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (worldIn.isClientSide) {
			if (((Player) entityIn).getMainHandItem().getItem() instanceof DShotgun) {
				while (Keybindings.RELOAD.consumeClick() && isSelected) {
					DoomPacketHandler.DSG.sendToServer(new DSGLoadingPacket(itemSlot));
				}
			}
		}
	}

	public static void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof DShotgun) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0
					&& user.getInventory().countItem(DoomItems.SHOTGUN_SHELLS.get()) > 0) {
				removeAmmo(DoomItems.SHOTGUN_SHELLS.get(), user);
				user.getItemInHand(hand).hurtAndBreak(-4, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
				user.getCommandSenderWorld().playSound((Player) null, user.getX(), user.getY(), user.getZ(),
						DoomSounds.SHOTGUNRELOAD.get(), SoundSource.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
		tooltip.add(Component.translatable("doom.doomed_credit.text").withStyle(ChatFormatting.RED)
				.withStyle(ChatFormatting.ITALIC));
		tooltip.add(Component.translatable("doom.doomed_credit1.text").withStyle(ChatFormatting.RED)
				.withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, worldIn, tooltip, flagIn);
	}

}