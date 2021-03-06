package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.DemonEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class DemonAttackGoal extends Goal {
	private int statecheck;
	private final DemonEntity entity;
	private double moveSpeedAmp = 1;
	private int attackTime = -1;

	public DemonAttackGoal(DemonEntity mob, double moveSpeedAmpIn, int state) {
		this.entity = mob;
		this.moveSpeedAmp = moveSpeedAmpIn;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		this.statecheck = state;
	}

	public boolean canStart() {
		return this.entity.getTarget() != null;
	}

	public boolean shouldContinue() {
		return this.canStart();
	}

	public void start() {
		super.start();
		this.entity.setAttacking(true);
	}

	public void stop() {
		super.stop();
		this.entity.setAttacking(false);
		this.entity.setAttackingState(0);
		this.attackTime = -1;
	}

	public void tick() {
		LivingEntity livingentity = this.entity.getTarget();
		if (livingentity != null) {
			boolean inLineOfSight = this.entity.getVisibilityCache().canSee(livingentity);
			this.attackTime++;
			this.entity.lookAtEntity(livingentity, 30.0F, 30.0F);
			double d0 = this.entity.squaredDistanceTo(livingentity.getX(), livingentity.getY(),
					livingentity.getZ());
			double d1 = this.getAttackReachSqr(livingentity);
			if (inLineOfSight) {
				if (this.entity.distanceTo(livingentity) >= 3.0D) {
					this.entity.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);
					this.attackTime = -5;
				} else {
					if (this.attackTime == 4) {
						this.entity.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);
						if (d0 <= d1) {
							this.entity.tryAttack(livingentity);
							this.entity.setAttackingState(statecheck);
						}
						livingentity.timeUntilRegen = 0;
					}
					if (this.attackTime == 8) {
						this.attackTime = -5;
						this.entity.setAttackingState(0);
					}
				}
			}
		}
	}

	protected double getAttackReachSqr(LivingEntity attackTarget) {
		return (double) (this.entity.getWidth() * 2.5F * this.entity.getWidth() * 2.5F + entity.getWidth());
	}

}