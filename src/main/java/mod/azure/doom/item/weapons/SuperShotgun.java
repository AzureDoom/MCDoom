package mod.azure.doom.item.weapons;

import java.util.function.Consumer;

import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.items.BaseGunItem;
import mod.azure.doom.client.Keybindings;
import mod.azure.doom.client.render.weapons.SSGRender;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.projectiles.MeatHookEntity;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import mod.azure.doom.util.PlayerProperties;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.packets.weapons.SSGLoadingPacket;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
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
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class SuperShotgun extends DoomBaseItem {

	public SuperShotgun() {
		super(new Item.Properties().stacksTo(1).durability(53));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return DoomTier.SHOTGUN.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
		if (entityLiving instanceof Player playerentity) {
			if (stack.getDamageValue() < stack.getMaxDamage() - 2) {
				if (playerentity.getMainHandItem().getItem() instanceof SuperShotgun) {
					playerentity.getCooldowns().addCooldown(this, 24);
					if (!worldIn.isClientSide) {
						var result = BaseGunItem.hitscanTrace(playerentity, 64, 1.0F);
						var enchantlevel = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
						if (result != null) {
							if (result.getEntity()instanceof LivingEntity livingEntity) {
								livingEntity.invulnerableTime = 0;
								livingEntity.setDeltaMovement(0, 0, 0);
								livingEntity.hurt(playerentity.damageSources().playerAttack(playerentity), DoomConfig.SERVER.shotgun_damage.get().floatValue() + enchantlevel * 2.0F);
								livingEntity.invulnerableTime = 0;
								livingEntity.setDeltaMovement(0, 0, 0);
								livingEntity.hurt(playerentity.damageSources().playerAttack(playerentity), DoomConfig.SERVER.shotgun_damage.get().floatValue() + enchantlevel * 2.0F);
							}
						} else {
							final var abstractarrowentity = createArrow(worldIn, stack, playerentity);
							abstractarrowentity.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() + 1, 0.0F, 1.0F * 3.0F, 1.0F);
							worldIn.addFreshEntity(abstractarrowentity);
							final var abstractarrowentity1 = createArrow(worldIn, stack, playerentity);
							abstractarrowentity1.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot() - 1, 0.0F, 1.0F * 3.0F, 1.0F);
							worldIn.addFreshEntity(abstractarrowentity1);
						}
						stack.hurtAndBreak(2, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
						worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), DoomSounds.SUPER_SHOTGUN_SHOOT.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
						triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) worldIn), "shoot_controller", "firing");
					}
					final var isInsideWaterBlock = playerentity.level.isWaterAt(playerentity.blockPosition());
					spawnLightSource(entityLiving, isInsideWaterBlock);
				}
			} else {
				worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), DoomSounds.EMPTY.get(), SoundSource.PLAYERS, 1.0F, 1.5F);
				((PlayerProperties) playerentity).setHasMeatHook(false);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide)
			if (stack.getItem() instanceof SuperShotgun)
				while (Keybindings.RELOAD.consumeClick() && selected)
					DoomPacketHandler.SUPERSHOTGUN.sendToServer(new SSGLoadingPacket(slot));
		if (((Player) entity).getMainHandItem().getItem() instanceof SuperShotgun && selected && ((PlayerProperties) entity).hasMeatHook())
			((PlayerProperties) entity).setHasMeatHook(false);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		final ItemStack stack = player.getOffhandItem();
		if (stack.getDamageValue() < stack.getMaxDamage() - 2) {
			if (!world.isClientSide() && stack.getItem() instanceof SuperShotgun) {
				player.getCooldowns().addCooldown(this, 5);
				if (!((PlayerProperties) player).hasMeatHook()) {
					final MeatHookEntity hookshot = new MeatHookEntity(world, player);
					hookshot.setProperties(stack, DoomConfig.SERVER.max_meathook_distance.get(), 10, player.getXRot(), player.getYRot(), 0f, 1.5f * (10 / 10));
					hookshot.getEntityData().set(MeatHookEntity.FORCED_YAW, player.getYRot());
					world.addFreshEntity(hookshot);
				}
				((PlayerProperties) player).setHasMeatHook(!((PlayerProperties) player).hasMeatHook());
			}
		}
		return super.use(world, player, hand);
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
		((PlayerProperties) user).setHasMeatHook(false);
		return super.finishUsingItem(stack, world, user);
	}

	public static float getArrowVelocity(int charge) {
		float f = charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	public static void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof SuperShotgun) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(DoomItems.SHOTGUN_SHELLS.get()) > 0) {
				removeAmmo(DoomItems.SHOTGUN_SHELLS.get(), user);
				user.getItemInHand(hand).hurtAndBreak(-4, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
			}
		}
	}

	public ShotgunShellEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		final float j = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		final ShotgunShellEntity arrowentity = new ShotgunShellEntity(worldIn, shooter, DoomConfig.SERVER.shotgun_damage.get().floatValue() + j * 2.0F);
		return arrowentity;
	}

	public static float getPullProgress(int useTicks) {
		float f = useTicks / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			private final SSGRender renderer = new SSGRender();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return renderer;
			}
		});
	}

}