package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;
import java.util.SplittableRandom;

import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.tiersuperheavy.GladiatorEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class RangedStrafeGladiatorAttackGoal extends Goal {
	private final GladiatorEntity entity;
	private double moveSpeedAmp = 1;
	private int attackTime = -1;

	private AbstractRangedAttack attack;

	public RangedStrafeGladiatorAttackGoal(GladiatorEntity mob, AbstractRangedAttack attack, double moveSpeedAmpIn) {
		this.entity = mob;
		this.moveSpeedAmp = moveSpeedAmpIn;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
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
		return (this.canUse() || !this.entity.getNavigation().isDone());
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void start() {
		super.start();
		this.entity.setAggressive(true);
		this.entity.setSilent(false);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by
	 * another one
	 */
	public void stop() {
		super.stop();
		this.entity.setAggressive(false);
		this.entity.setAttackingState(0);
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
			boolean inLineOfSight = this.entity.getSensing().hasLineOfSight(livingentity);
			double d0 = this.entity.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			double d1 = this.getAttackReachSqr(livingentity);
			this.attackTime++;
			if (inLineOfSight) {
				if (this.entity.distanceTo(livingentity) >= 6.0D) {
					if (this.entity.getDeathState() == 0) {
						if (this.attackTime == 1) {
							this.entity.getNavigation().stop();
							this.entity.setAttackingState(1);
							this.entity.setTextureState(1);
							this.entity.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
						}
						if (this.attackTime == 23) {
							this.entity.setTextureState(2);
						}
						if (this.attackTime == 40) {
							this.attack.shoot();
						}
						if (this.attackTime == 48) {
							this.entity.setAttackingState(0);
							this.entity.setTextureState(0);
						}
						if (this.attackTime == 83) {
							this.attackTime = -150;
							this.entity.setTextureState(0);
							this.entity.setAttackingState(0);
							this.entity.getNavigation().moveTo(livingentity, this.moveSpeedAmp);
						}
					} else {
						this.attackTime = -150;
						this.entity.setTextureState(0);
						this.entity.setAttackingState(0);
						this.entity.getNavigation().moveTo(livingentity, this.moveSpeedAmp);
					}
				} else {
					this.entity.getNavigation().moveTo(livingentity, this.moveSpeedAmp);
					this.entity.setSilent(true);
					if (d0 <= d1) {
						if (this.attackTime == 1) {
							SplittableRandom random = new SplittableRandom();
							int var = random.nextInt(0, 3);
							if (var == 1) {
								this.entity.setAttackingState(3);
							} else {
								this.entity.setAttackingState(3);
							}
						}
						if (this.attackTime == 18) {
							this.entity.doHurtTarget(livingentity);
						}
						if (this.attackTime == 25) {
							this.attackTime = -25;
							this.entity.setAttackingState(0);
							this.entity.setSilent(false);
						}
					} else {
						this.entity.setAttackingState(0);
						this.attackTime = -25;
					}
				}
			}
		}
	}

	protected double getAttackReachSqr(LivingEntity attackTarget) {
		return (double) (this.entity.getBbWidth() * 2.0F * this.entity.getBbWidth() * 2.0F + attackTarget.getBbWidth());
	}
}
