package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.tiersuperheavy.GladiatorEntity;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.particle.ParticleTypes;

public class RangedStrafeGladiatorAttackGoal extends Goal {
	private final GladiatorEntity entity;
	private double moveSpeedAmp = 1;
	private int attackTime = -1;

	private AbstractRangedAttack attack;

	public RangedStrafeGladiatorAttackGoal(GladiatorEntity mob, AbstractRangedAttack attack, double moveSpeedAmpIn) {
		this.entity = mob;
		this.moveSpeedAmp = moveSpeedAmpIn;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		this.attack = attack;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */
	public boolean canStart() {
		return this.entity.getTarget() != null;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinue() {
		return this.canStart();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void start() {
		super.start();
		this.entity.setAttacking(true);
		this.entity.setSilent(false);
		this.entity.setTextureState(0);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by
	 * another one
	 */
	public void stop() {
		super.stop();
		this.entity.setAttacking(false);
		this.entity.setAttackingState(0);
		this.entity.setTextureState(0);
		this.attackTime = -1;
		this.entity.stopUsingItem();
		this.entity.setSilent(false);
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void tick() {
		LivingEntity livingentity = this.entity.getTarget();
		if (livingentity != null) {
			boolean inLineOfSight = this.entity.getVisibilityCache().canSee(livingentity);
			this.attackTime++;
			this.entity.lookAtEntity(livingentity, 30.0F, 30.0F);
			double d0 = this.entity.squaredDistanceTo(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			double d1 = this.getAttackReachSqr(livingentity);
			if (inLineOfSight) {
				if (this.entity.distanceTo(livingentity) >= 6.0D) {
					if (this.entity.getDeathState() == 0) {
						if (this.attackTime == 1) {
							this.entity.getNavigation().stop();
							this.entity.setAttackingState(1);
							this.entity.setTextureState(1);
						}
						if (this.attackTime == 20) {
							this.entity.setTextureState(2);
						}
						if (this.attackTime == 23) {
							AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(entity.world,
									entity.getX(), entity.getY(), entity.getZ());
							areaeffectcloudentity.setParticleType(ParticleTypes.SOUL_FIRE_FLAME);
							areaeffectcloudentity.setRadius(3.0F);
							areaeffectcloudentity.setDuration(10);
							areaeffectcloudentity.setPos(entity.getX(), entity.getY(), entity.getZ());
							entity.world.spawnEntity(areaeffectcloudentity);
							this.attack.shoot();
							this.entity.setTextureState(0);
							this.entity.setAttackingState(0);
						}
						if (this.attackTime == 48) {
							this.entity.setAttackingState(0);
							this.entity.setTextureState(0);
						}
						if (this.attackTime == 83) {
							this.attackTime = -5;
							this.entity.setTextureState(0);
							this.entity.setAttackingState(0);
							this.entity.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);
						}
					} else {
						this.attackTime = -25;
						this.entity.setTextureState(0);
						this.entity.setAttackingState(0);
						this.entity.getNavigation().startMovingTo(livingentity, 1.4);
					}
				} else {
					this.entity.getNavigation().startMovingTo(livingentity, 1.4);
					this.entity.setSilent(true);
					if (this.attackTime == 1) {
						this.entity.setAttackingState(3);
					}
					if (this.attackTime == 18) {
						this.entity.getNavigation().stop();
						AreaEffectCloudEntity areaeffectcloudentity = new AreaEffectCloudEntity(entity.world,
								entity.getX(), entity.getY(), entity.getZ());
						areaeffectcloudentity.setParticleType(ParticleTypes.SMOKE);
						areaeffectcloudentity.setRadius(3.0F);
						areaeffectcloudentity.setDuration(55);
						areaeffectcloudentity.setPos(entity.getX(), entity.getY(), entity.getZ());
						entity.world.spawnEntity(areaeffectcloudentity);
						if (d0 <= d1) {
							this.entity.tryAttack(livingentity);
						}
						livingentity.timeUntilRegen = 0;
					}
					if (this.attackTime == 19) {
						if (this.entity.getDeathState() == 1) {
							if (d0 <= d1) {
								this.entity.tryAttack1(livingentity);
							}
							livingentity.timeUntilRegen = 0;
						}
						this.entity.setAttackingState(0);
					}
					if (this.attackTime == 25) {
						this.attackTime = -5;
						this.entity.setAttackingState(0);
						this.entity.setSilent(false);
					}
				}
			}
		}
	}

	protected double getAttackReachSqr(LivingEntity attackTarget) {
		return (double) (this.entity.getWidth() * 2.0F * this.entity.getWidth() * 2.0F + attackTarget.getWidth());
	}
}
