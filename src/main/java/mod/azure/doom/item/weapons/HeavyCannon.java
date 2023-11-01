package mod.azure.doom.item.weapons;

import mod.azure.azurelib.Keybindings;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.animatable.client.RenderProvider;
import mod.azure.azurelib.items.BaseGunItem;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.render.weapons.HeavyCannonRender;
import mod.azure.doom.entity.projectiles.BulletEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.packets.weapons.HeavyCannonLoadingPacket;
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

import java.util.function.Consumer;
import java.util.function.Supplier;

public class HeavyCannon extends DoomBaseItem {

	private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);

	public HeavyCannon() {
		super(new Item.Properties().stacksTo(1).durability(201));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return DoomTier.PISTOL.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public void onUseTick(Level worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof Player playerentity) {
			if (stack.getDamageValue() < stack.getMaxDamage() - 1 && !playerentity.getCooldowns().isOnCooldown(this)) {
				playerentity.getCooldowns().addCooldown(this, 4);
				if (!worldIn.isClientSide) {
					var result = BaseGunItem.hitscanTrace(playerentity, 64, 1.0F);
					var enchantlevel = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
					if (result != null) {
						if (result.getEntity() instanceof LivingEntity livingEntity)
							livingEntity.hurt(playerentity.damageSources().playerAttack(playerentity), DoomMod.config.bullet_damage + enchantlevel * 2.0F);
					} else {
						final var bullet = createArrow(worldIn, stack, playerentity);
						bullet.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 1.0F * 3.0F, 1.0F);
						bullet.setParticle(2);
						bullet.isNoGravity();
						worldIn.addFreshEntity(bullet);
					}
					stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
					worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), DoomSounds.HEAVY_CANNON.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
					triggerAnim(playerentity, GeoItem.getOrAssignId(stack, (ServerLevel) worldIn), "shoot_controller", "firing");
				}
				final boolean isInsideWaterBlock = playerentity.level().isWaterAt(playerentity.blockPosition());
				spawnLightSource(entityLiving, isInsideWaterBlock);
			} else {
				worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), DoomSounds.EMPTY.get(), SoundSource.PLAYERS, 1.0F, 1.5F);
			}
		}
	}

	public static void reload(Player user, InteractionHand hand) {
		if (user.getMainHandItem().getItem() instanceof HeavyCannon) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(DoomItems.BULLETS.get()) > 0) {
				removeAmmo(DoomItems.BULLETS.get(), user);
				user.getMainHandItem().hurtAndBreak(-10, user, s -> user.broadcastBreakEvent(hand));
				user.getMainHandItem().setPopTime(3);
				user.getCommandSenderWorld().playSound((Player) null, user.getX(), user.getY(), user.getZ(), DoomSounds.CLIPRELOAD.get(), SoundSource.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide)
			if (stack.getItem() instanceof HeavyCannon)
				while (Keybindings.RELOAD.consumeClick() && selected)
					DoomPacketHandler.HEAVYCANNON.sendToServer(new HeavyCannonLoadingPacket(slot));
	}

	public BulletEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		final float j = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		final BulletEntity arrowentity = new BulletEntity(worldIn, shooter, DoomMod.config.bullet_damage + j * 2.0F);
		return arrowentity;
	}

	public static float getArrowVelocity(int charge) {
		float f = charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;
		if (f > 1.0F) {
			f = 1.0F;
		}

		return f;
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
	public void createRenderer(Consumer<Object> consumer) {
		consumer.accept(new RenderProvider() {
			private final HeavyCannonRender renderer = new HeavyCannonRender();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return renderer;
			}
		});
	}

	@Override
	public Supplier<Object> getRenderProvider() {
		return renderProvider;
	}
}