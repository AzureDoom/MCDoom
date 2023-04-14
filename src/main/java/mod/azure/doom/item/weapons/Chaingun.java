package mod.azure.doom.item.weapons;

import java.util.function.Consumer;

import mod.azure.azurelib.animatable.SingletonGeoAnimatable;
import mod.azure.azurelib.items.BaseGunItem;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.Keybindings;
import mod.azure.doom.client.render.weapons.ChaingunRender;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.projectiles.ChaingunBulletEntity;
import mod.azure.doom.util.enums.DoomTier;
import mod.azure.doom.util.packets.DoomPacketHandler;
import mod.azure.doom.util.packets.weapons.ChaingunLoadingPacket;
import mod.azure.doom.util.registry.DoomItems;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class Chaingun extends DoomBaseItem {

	public Chaingun() {
		super(new Item.Properties().stacksTo(1).durability(201).tab(DoomMod.DoomWeaponItemGroup));
		SingletonGeoAnimatable.registerSyncedAnimatable(this);
	}

	@Override
	public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
		return DoomTier.CHAINGUN.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
	}

	@Override
	public void onUseTick(Level worldIn, LivingEntity entityLiving, ItemStack stack, int count) {
		if (entityLiving instanceof Player playerentity) {
			if (stack.getDamageValue() < stack.getMaxDamage() - 1) {
				if (!playerentity.getCooldowns().isOnCooldown(this)) {
					playerentity.getCooldowns().addCooldown(this, 3);
					if (!worldIn.isClientSide) {
						var result = BaseGunItem.hitscanTrace(playerentity, 64, 1.0F);
						var enchantlevel = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
						if (result != null) {
							if (result.getEntity()instanceof LivingEntity livingEntity)
								livingEntity.hurt(DamageSource.playerAttack(playerentity), DoomConfig.SERVER.chaingun_bullet_damage.get().floatValue() + enchantlevel * 2.0F);
						} else {
							final var bullet = createArrow(worldIn, stack, playerentity);
							bullet.shootFromRotation(playerentity, playerentity.getXRot(), playerentity.getYRot(), 0.0F, 1.0F * 3.0F, 1.0F);
							bullet.isNoGravity();
							worldIn.addFreshEntity(bullet);
						}
						stack.hurtAndBreak(1, entityLiving, p -> p.broadcastBreakEvent(entityLiving.getUsedItemHand()));
						final boolean isInsideWaterBlock = playerentity.level.isWaterAt(playerentity.blockPosition());
						spawnLightSource(entityLiving, isInsideWaterBlock);
					}
				} else {
					worldIn.playSound((Player) null, playerentity.getX(), playerentity.getY(), playerentity.getZ(), DoomSounds.EMPTY.get(), SoundSource.PLAYERS, 1.0F, 1.5F);
				}
			}
		}
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
		if (user.getItemInHand(hand).getItem() instanceof Chaingun) {
			while (!user.isCreative() && user.getItemInHand(hand).getDamageValue() != 0 && user.getInventory().countItem(DoomItems.CHAINGUN_BULLETS.get()) > 0) {
				removeAmmo(DoomItems.CHAINGUN_BULLETS.get(), user);
				user.getItemInHand(hand).hurtAndBreak(-50, user, s -> user.broadcastBreakEvent(hand));
				user.getItemInHand(hand).setPopTime(3);
				user.getCommandSenderWorld().playSound((Player) null, user.getX(), user.getY(), user.getZ(), DoomSounds.CLIPRELOAD.get(), SoundSource.PLAYERS, 1.00F, 1.0F);
			}
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world.isClientSide)
			if (stack.getItem() instanceof Chaingun)
				while (Keybindings.RELOAD.consumeClick() && selected)
					DoomPacketHandler.CHAINGUN.sendToServer(new ChaingunLoadingPacket(slot));
	}

	public ChaingunBulletEntity createArrow(Level worldIn, ItemStack stack, LivingEntity shooter) {
		final float j = EnchantmentHelper.getTagEnchantmentLevel(Enchantments.POWER_ARROWS, stack);
		final ChaingunBulletEntity arrowentity = new ChaingunBulletEntity(worldIn, shooter, DoomConfig.SERVER.chaingun_bullet_damage.get().floatValue() + j * 2.0F);
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
			private final ChaingunRender renderer = new ChaingunRender();

			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return renderer;
			}
		});
	}
}