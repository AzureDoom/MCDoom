package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;
import java.util.SplittableRandom;

import mod.azure.doom.entity.attack.AbstractDoubleRangedAttack;
import mod.azure.doom.entity.tierboss.GladiatorEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;

public class RangedStrafeGladiatorAttackGoal extends Goal {
	private final GladiatorEntity entity;
	private int attackTime = -1;
	private int summonTime = -1;
	private AbstractDoubleRangedAttack attack;

	public RangedStrafeGladiatorAttackGoal(GladiatorEntity mob, AbstractDoubleRangedAttack attack) {
		this.entity = mob;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
		this.attack = attack;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */
	public boolean canUse() {
		return this.entity.getTarget() != null;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean canContinueToUse() {
		return this.canUse();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void start() {
		super.start();
		this.entity.setAggressive(true);
		this.entity.setSilent(false);
		this.entity.setTextureState(0);
		this.entity.level.explode(this.entity, this.entity.getX(), this.entity.getY() + 5D, this.entity.getZ(), 3.0F, false,
				Explosion.BlockInteraction.BREAK);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by
	 * another one
	 */
	public void stop() {
		super.stop();
		this.entity.setAggressive(false);
		this.entity.setAttackingState(0);
		this.entity.setTextureState(0);
		this.attackTime = -1;
		this.entity.stopUsingItem();
		this.entity.setSilent(false);
		this.entity.level.explode(this.entity, this.entity.getX(), this.entity.getY() + 5D, this.entity.getZ(), 3.0F, false,
				Explosion.BlockInteraction.BREAK);
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void tick() {
		LivingEntity livingentity = this.entity.getTarget();
		if (livingentity != null) {
			boolean inLineOfSight = this.entity.getSensing().hasLineOfSight(livingentity);
			this.attackTime++;
			this.entity.lookAt(livingentity, 30.0F, 30.0F);
			SplittableRandom random = new SplittableRandom();
			int randomRangedPhaseOne = random.nextInt(0, 5);
			double d0 = this.entity.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			double d1 = this.getAttackReachSqr(livingentity);
			if (inLineOfSight) {
				if (this.entity.distanceTo(livingentity) >= 3.0D) {
					if (this.entity.getDeathState() == 0 && this.summonTime > 10) {
						if (randomRangedPhaseOne <= 3) {
							if (this.attackTime == 1) {
								this.entity.getNavigation().stop();
								this.entity.setAttackingState(1);
								this.entity.setTextureState(1);
							}
							if (this.attackTime == 20) {
								this.entity.setTextureState(2);
							}
							if (this.attackTime == 23) {
								AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(entity.level, entity.getX(),
										entity.getY(), entity.getZ());
								areaeffectcloudentity.setParticle(ParticleTypes.SOUL_FIRE_FLAME);
								areaeffectcloudentity.setRadius(3.0F);
								areaeffectcloudentity.setDuration(10);
								areaeffectcloudentity.setPos(entity.getX(), entity.getY(), entity.getZ());
								entity.level.addFreshEntity(areaeffectcloudentity);
								this.attack.shoot();
								this.entity.setTextureState(0);
								this.summonTime = -300;
							}
							if (this.attackTime == 48) {
								this.entity.setAttackingState(0);
								this.entity.setTextureState(0);
							}
							if (this.attackTime == 83) {
								this.attackTime = -5;
								this.entity.setTextureState(0);
								this.entity.setAttackingState(0);
								this.entity.getNavigation().moveTo(livingentity, 1.0);
							}
						}
					} else if (this.entity.getDeathState() == 1 && this.summonTime == 2) {
						if (this.attackTime == 1) {
							this.entity.getNavigation().stop();
						}
						if (this.attackTime == 5) {
							this.entity.setAttackingState(4);
						}
						if (this.attackTime == 8) {
							this.attack.shoot2();
							this.summonTime = -8;
						}
						if (this.attackTime == 13) {
							this.entity.getNavigation().moveTo(livingentity, 1.25);
							this.attackTime = -5;
						}
					} else {
						this.summonTime++;
						this.attackTime = -25;
						this.entity.setTextureState(0);
						this.entity.setAttackingState(0);
						this.entity.getNavigation().moveTo(livingentity, 1.0);
					}
				} else {
					this.entity.getNavigation().moveTo(livingentity, this.entity.getDeathState() == 0 ? 1.0 : 1.25);
					if (this.attackTime == 1) {
						this.entity.setAttackingState(3);
						this.summonTime++;
						Vec3 vec3d = this.entity.getDeltaMovement();
						Vec3 vec3d2 = new Vec3(livingentity.getX() - this.entity.getX(), 0.0,
								livingentity.getZ() - this.entity.getZ());
						vec3d2 = vec3d2.normalize().scale(0.4).add(vec3d.scale(0.4));
						this.entity.setDeltaMovement(vec3d2.x, 0.5F, vec3d2.z);
					}
					if (this.attackTime == 18) {
						this.entity.getNavigation().stop();
						AreaEffectCloud areaeffectcloudentity = new AreaEffectCloud(entity.level, entity.getX(),
								entity.getY(), entity.getZ());
						areaeffectcloudentity.setParticle(ParticleTypes.SMOKE);
						areaeffectcloudentity.setRadius(3.0F);
						areaeffectcloudentity.setDuration(2);
						areaeffectcloudentity.setPos(entity.getX(), entity.getY(), entity.getZ());
						entity.level.addFreshEntity(areaeffectcloudentity);
						if (d0 <= d1) {
							this.entity.doHurtTarget(livingentity);
						}
						livingentity.invulnerableTime = 0;
					}
					if (this.attackTime == 19) {
						if (this.entity.getDeathState() == 1) {
							if (d0 <= d1) {
								this.entity.doHurtTarget1(livingentity);
							}
							livingentity.invulnerableTime = 0;
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
		return (double) (this.entity.getBbWidth() * 2.0F * this.entity.getBbWidth() * 2.0F + attackTarget.getBbWidth());
	}
}
