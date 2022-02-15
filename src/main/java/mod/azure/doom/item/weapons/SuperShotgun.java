package mod.azure.doom.item.weapons;

import java.util.function.Consumer;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.Keybindings;
import mod.azure.doom.client.render.weapons.SSGRender;
import mod.azure.doom.entity.projectiles.MeatHookEntity;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import mod.azure.doom.util.PlayerProperties;
import mod.azure.doom.util.config.DoomConfig;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.packets.weapons.SSGLoadingPacket;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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

public class SuperShotgun extends DoomBaseItem {

	public SuperShotgun() {
		super(new Item.Properties().tab(DoomMod.DoomWeaponItemGroup).stacksTo(1).durability(53));
	}

	@Override
	public void initializeClient(Consumer<IItemRenderProperties> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IItemRenderProperties() {
			private final BlockEntityWithoutLevelRenderer renderer = new SSGRender();

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
			if (stack.getDamageValue() < (stack.getMaxDamage() - 2)) {
				if (playerentity.getMainHandItem().getItem() instanceof SuperShotgun) {
					playerentity.getCooldowns().addCooldown(this, 24);
					if (!worldIn.isClientSide) {
						ShotgunShellEntity abstractarrowentity = createArrow(worldIn, stack, playerentity);
						abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(),
								playerentity.getYRot() + 1, 0.0F, 1.0F * 3.0F, 1.0F);
						abstractarrowentity.isNoGravity();
						worldIn.addFreshEntity(abstractarrowentity);
						ShotgunShellEntity abstractarrowentity1 = createArrow(worldIn, stack, playerentity);
						abstractarrowentity1.shootFromRotation(playerentity, playerentity.getXRot(),
								playerentity.getYRot() - 1, 0.0F, 1.0F * 3.0F, 1.0F);
						abstractarrowentity1.isNoGravity();
						worldIn.addFreshEntity(abstractarrowentity1);

						stack.hurtAndBreak(2, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
						worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
								ModSoundEvents.SUPER_SHOTGUN_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
						if (!worldIn.isClientSide) {
							final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerLevel) worldIn);
							final PacketDistributor.PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF
									.with(() -> playerentity);
							GeckoLibNetwork.syncAnimation(target, this, id, ANIM_OPEN);
						}
					}
				}
			} else {
				worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(),
						ModSoundEvents.EMPTY.get(), SoundSource.PLAYERS, 1.0F, 1.5F);
				((PlayerProperties) playerentity).setHasMeatHook(false);
			}
		}
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		ItemStack stack = player.getOffhandItem();
		if (stack.getDamageValue() < (stack.getMaxDamage() - 2)) {
			if (!world.isClientSide() && stack.getItem() instanceof SuperShotgun) {
				player.getCooldowns().addCooldown(this, 5);
				if (!((PlayerProperties) player).hasMeatHook()) {
					MeatHookEntity hookshot = new MeatHookEntity(world, player);
					hookshot.setProperties(stack, DoomConfig.SERVER.max_meathook_distance.get(), 10, player.getXRot(),
							player.getYRot(), 0f, 1.5f * (float) (10 / 10));
					hookshot.getEntityData().set(MeatHookEntity.FORCED_YAW, player.getYRot());
					world.addFreshEntity(hookshot);
				}
				((PlayerProperties) player).setHasMeatHook(!((PlayerProperties) player).hasMeatHook());
			}
		}
		return super.use(world, player, hand);
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
			if (((Player) entityIn).getMainHandItem().getItem() instanceof SuperShotgun) {
				while (Keybindings.RELOAD.consumeClick() && isSelected) {
					DoomPacketHandler.SUPERSHOTGUN.sendToServer(new SSGLoadingPacket(itemSlot));
				}
			}
		}
		if (((Player) entityIn).getMainHandItem().getItem() instanceof SuperShotgun && isSelected
				&& ((PlayerProperties) entityIn).hasMeatHook()) {
			((PlayerProperties) entityIn).setHasMeatHook(false);
		}
	}

	public static void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof SuperShotgun) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0
					&& user.getInventory().countItem(DoomItems.SHOTGUN_SHELLS.get()) > 0) {
				removeAmmo(DoomItems.SHOTGUN_SHELLS.get(), user);
				user.getItemInHand(hand).hurtAndBreak(-4, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
			}
		}
	}
}