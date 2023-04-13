package mod.azure.doom.item.weapons;

import java.util.function.Consumer;
import java.util.function.Supplier;

import io.netty.buffer.Unpooled;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.items.BaseGunItem;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.ClientInit;
import mod.azure.doom.client.render.weapons.SSGRender;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.projectiles.MeatHookEntity;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import mod.azure.doom.util.PlayerProperties;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.FriendlyByteBuf;
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

public class SuperShotgun extends DoomBaseItem {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

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
						var enchantlevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
						if (result != null) {
							if (result.getEntity()instanceof LivingEntity livingEntity) {
								livingEntity.invulnerableTime = 0;
								livingEntity.setDeltaMovement(0, 0, 0);
								livingEntity.hurt(playerentity.damageSources().playerAttack(playerentity), DoomConfig.shotgun_damage + enchantlevel * 2.0F);
								livingEntity.invulnerableTime = 0;
								livingEntity.setDeltaMovement(0, 0, 0);
								livingEntity.hurt(playerentity.damageSources().playerAttack(playerentity), DoomConfig.shotgun_damage + enchantlevel * 2.0F);
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
						worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), DoomSounds.SUPER_SHOTGUN_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F);
						triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) worldIn), "shoot_controller", "firing");
					}
					final boolean isInsideWaterBlock = playerentity.level.isWaterAt(playerentity.blockPosition());
					spawnLightSource(entityLiving, isInsideWaterBlock);
				}
			} else {
				worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), DoomSounds.EMPTY, SoundSource.PLAYERS, 1.0F, 1.5F);
				((PlayerProperties) playerentity).setHasMeatHook(false);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide)
			if (ClientInit.reload.consumeClick() && selected) {
				final var passedData = new FriendlyByteBuf(Unpooled.buffer());
				passedData.writeBoolean(true);
				ClientPlayNetworking.send(DoomMod.SUPERSHOTGUN, passedData);
			}
		if (((Player) entity).getMainHandItem().getItem() instanceof SuperShotgun && selected && ((PlayerProperties) entity).hasMeatHook())
			((PlayerProperties) entity).setHasMeatHook(false);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
		final var stack = player.getOffhandItem();
		if (stack.getDamageValue() < stack.getMaxDamage() - 2) {
			if (!world.isClientSide() && stack.getItem() instanceof SuperShotgun) {
				player.getCooldowns().addCooldown(this, 5);
				if (!((PlayerProperties) player).hasMeatHook()) {
					final var hookshot = new MeatHookEntity(world, player);
					hookshot.setProperties(stack, DoomConfig.max_meathook_distance, 10, player.getXRot(), player.getYRot(), 0f, 1.5f * (10 / 10));
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
		var f = charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F)
			f = 1.0F;

		return f;
	}

	public static void reload(Player user, InteractionHand hand) {
		if (user.getItemInHand(hand).getItem() instanceof SuperShotgun) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(DoomItems.SHOTGUN_SHELLS) > 0) {
				removeAmmo(DoomItems.SHOTGUN_SHELLS, user);
				user.getItemInHand(hand).hurtAndBreak(-4, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
			}
		}
	}

	public ShotgunShellEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		final var enchantlevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		final var shell = new ShotgunShellEntity(worldIn, shooter, DoomConfig.shotgun_damage + enchantlevel * 2.0F);
		return shell;
	}

	public static float getPullProgress(int useTicks) {
		var f = useTicks / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F)
			f = 1.0F;

		return f;
	}

	@Override
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private SSGRender renderer = null;

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				if (renderer == null)
					return new SSGRender();
				return this.renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return renderProvider;
	}

}