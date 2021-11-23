package mod.azure.doom.entity.ai.goal;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.CustomSmallFireballEntity;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import mod.azure.doom.entity.tierheavy.Revenant2016Entity;
import mod.azure.doom.util.registry.ModSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class FlyingRangeAttackGoal extends Goal {
	private final DemonEntity actor;
	private float damage;
	private final double speed = 1.0D;
	private int cooldown = -1;
	private int targetSeeingTicker;
	private SoundEvent sound;
	private boolean rockets;

	public FlyingRangeAttackGoal(DemonEntity zombie, float damage, SoundEvent firingsound, boolean useRockets) {
		this.actor = zombie;
		this.damage = damage;
		this.sound = firingsound;
		this.rockets = useRockets;
	}

	public boolean canUse() {
		return this.actor.getTarget() == null ? false : true;
	}

	public boolean canContinueToUse() {
		return (this.canUse() || !this.actor.getNavigation().isDone());
	}

	public void start() {
		super.start();
		this.actor.setAggressive(true);
		this.actor.setAttackingState(0);
	}

	public void stop() {
		super.stop();
		this.actor.setAggressive(false);
		this.targetSeeingTicker = 0;
		this.cooldown = -1;
		this.actor.setAttackingState(0);
	}

	public void tick() {
		LivingEntity livingEntity = this.actor.getTarget();
		if (livingEntity != null) {
			boolean bl = this.actor.getSensing().hasLineOfSight(livingEntity);
			boolean bl2 = this.targetSeeingTicker > 0;
			++this.cooldown;
			if (bl != bl2) {
				this.targetSeeingTicker = 0;
			}

			if (bl) {
				++this.targetSeeingTicker;
			} else {
				--this.targetSeeingTicker;
			}
			this.actor.lookAt(livingEntity, 30.0F, 30.0F);
			if (bl) {
				if (rockets == true) {
					if (this.cooldown == 15 && this.targetSeeingTicker >= -60) {
						this.shootRocket(livingEntity);
					}
					if (this.cooldown == 20 && this.targetSeeingTicker >= -60) {
						this.shootRocket(livingEntity);
					}
					if (this.cooldown == 45) {
						this.cooldown = -150;
						this.actor.setAttackingState(0);
						this.actor.getNavigation().moveTo(livingEntity, this.speed);
					}
				} else {
					if (this.cooldown == 15 && this.targetSeeingTicker >= -60) {
						this.shootFireball(livingEntity);
					}
					if (this.cooldown == 25) {
						this.cooldown = -150;
						this.actor.setAttackingState(0);
						this.actor.getNavigation().moveTo(livingEntity, this.speed);
					}
				}
			}
		}
	}

	public void shootFireball(LivingEntity livingentity) {
		Level world = this.actor.level;
		Vec3 vec3d = this.actor.getViewVector(1.0F);
		double f = livingentity.getX() - (this.actor.getX() + vec3d.x * 2.0D);
		double g = livingentity.getY(0.5D) - (0.5D + this.actor.getY(0.5D));
		double h = livingentity.getZ() - (this.actor.getZ() + vec3d.z * 2.0D);
		CustomSmallFireballEntity fireballEntity = new CustomSmallFireballEntity(world, this.actor, f, g, h, damage);
		fireballEntity.setPos(this.actor.getX() + vec3d.x * 2.0D, this.actor.getY(0.5D) + 0.75D,
				actor.getZ() + vec3d.z * 2.0D);
		actor.level.playSound(null, actor, sound, SoundSource.HOSTILE, 0.5F, 1.0F);
		world.addFreshEntity(fireballEntity);
		this.actor.setAttackingState(1);
	}

	public void shootRocket(LivingEntity livingentity) {
		Level world = this.actor.level;
		Vec3 vec3d = this.actor.getViewVector(1.0F);
		double f = livingentity.getX() - (this.actor.getX() + vec3d.x * 2.0D);
		double g = livingentity.getY(0.5D) - (0.5D + this.actor.getY(0.5D));
		double h = livingentity.getZ() - (this.actor.getZ() + vec3d.z * 2.0D);
		RocketMobEntity fireballEntity = new RocketMobEntity(world, this.actor, f, g, h, damage);
		fireballEntity.setPos(this.actor.getX() + vec3d.x * 2.0D, this.actor.getY(0.5D) + 0.75D,
				actor.getZ() + vec3d.z * 2.0D);
		actor.level.playSound(null, actor,
				(((Revenant2016Entity) actor).getVariant() == 10 ? ModSoundEvents.REVENANT_DOOT.get()
						: ModSoundEvents.REVENANT_ATTACK.get()),
				SoundSource.HOSTILE, 0.5F, 1.0F);
		world.addFreshEntity(fireballEntity);
	}
}