package mod.azure.doom.util;

import java.util.HashMap;

import mod.azure.doom.util.registry.DoomEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

/**
 * 
 * @credit to https://gitlab.com/modding-legacy/
 *
 */
public class DoomLeapEntityEvents {
	public static HashMap<String, Boolean> uuidHasJumpedMap = new HashMap<String, Boolean>();
	int cooldown = 0;
	int jumpCount = 0;

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event) {
		LivingEntity entity = event.getEntityLiving();

		if (entity instanceof PlayerEntity && uuidHasJumpedMap.containsKey(entity.getUUID().toString())) {
			float enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(DoomEnchantments.LEAPING_DOOM.get(), entity);
			boolean usedMidAirJump = uuidHasJumpedMap.get(entity.getUUID().toString());
			boolean playerJumping = ObfuscationReflectionHelper.getPrivateValue(LivingEntity.class, entity,
					"field_70703_bu");
			boolean canJump = !entity.isOnGround() && !usedMidAirJump;
			if (entity.isOnGround() || this.jumpCount > 2) {
				uuidHasJumpedMap.put(entity.getUUID().toString(), false);
			}

			if (!(enchantmentLevel > 0))
				return;

			if (cooldown <= 0 && canJump && !entity.isFallFlying() && !((PlayerEntity) entity).isCreative()
					&& !((PlayerEntity) entity).abilities.flying && !((PlayerEntity) entity).verticalCollision) {
				this.jumpCount = this.getMultiJumps();
				if (playerJumping && this.jumpCount > 0 && entity.getDeltaMovement().y() < 0.333) {
					this.jumpCount--;
					uuidHasJumpedMap.put(entity.getUUID().toString(), true);
					Vector3d playerLook = entity.getViewVector(1);
					Vector3d dashVec = new Vector3d(playerLook.x, entity.getDeltaMovement().y, playerLook.z);
					entity.setDeltaMovement(dashVec);
					entity.push(0, (double) 0.2F * 2.0D, 0);
					entity.playSound(SoundEvents.FIRE_EXTINGUISH, 1.0F, 2.0F);
					if (!entity.isOnGround()) {
						if (entity instanceof ServerPlayerEntity) {
							entity.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, 100, 4));
						}
					}
					for (int i = 0; i < 20; ++i) {
						double d0 = entity.level.random.nextGaussian() * 0.02D;
						double d1 = entity.level.random.nextGaussian() * 0.02D;
						double d2 = entity.level.random.nextGaussian() * 0.02D;
						entity.level.addParticle(ParticleTypes.CRIT,
								entity.getX() + (double) (entity.level.random.nextFloat() * entity.getBbWidth() * 2.0F)
										- (double) entity.getBbWidth() - d0 * 10.0D,
								entity.getY() - d1 * 10.0D,
								entity.getZ() + (double) (entity.level.random.nextFloat() * entity.getBbWidth() * 2.0F)
										- (double) entity.getBbWidth() - d2 * 10.0D,
								d0, d1, d2);
					}
					cooldown = 25;
				}

			}
		} else if (entity instanceof PlayerEntity) {
			uuidHasJumpedMap.put(entity.getUUID().toString(), false);
		}
	}

	private int getMultiJumps() {
		int jumpCount = 0;
		jumpCount += 1;
		return jumpCount;
	}

	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		if (cooldown > 0)
			--cooldown;
	}

	@SubscribeEvent
	public void onLivingFall(LivingFallEvent event) {
		float enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(DoomEnchantments.LEAPING_DOOM.get(),
				event.getEntityLiving());

		if (enchantmentLevel > 0) {
			event.setDamageMultiplier(0);
			event.setCanceled(true);
			event.setDistance(0);
			event.setDamageMultiplier(0F);
		}
	}

}
