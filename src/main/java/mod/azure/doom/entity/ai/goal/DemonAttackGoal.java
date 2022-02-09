package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.DemonEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class DemonAttackGoal extends Goal {
	private int statecheck;
	private final DemonEntity entity;
	private double moveSpeedAmp = 1;
	private int attackTime = -1;

	public DemonAttackGoal(DemonEntity mob, double moveSpeedAmpIn, int state) {
		this.entity = mob;
		this.moveSpeedAmp = moveSpeedAmpIn;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		this.statecheck = state;
	}

	public boolean canUse() {
		return this.entity.getTarget() != null;
	}

	public boolean canContinueToUse() {
		return this.canUse();
	}

	public void start() {
		super.start();
		this.entity.setAggressive(true);
	}

	public void stop() {
		super.stop();
		this.entity.setAggressive(false);
		this.entity.setAttackingState(0);
		this.attackTime = -1;
	}

	public void tick() {
		LivingEntity livingentity = this.entity.getTarget();
		if (livingentity != null) {
			boolean inLineOfSight = this.entity.getSensing().hasLineOfSight(livingentity);
			this.attackTime++;
			this.entity.lookAt(livingentity, 30.0F, 30.0F);
			if (inLineOfSight) {
				if (this.entity.distanceTo(livingentity) >= 3.0D) {
					this.entity.getNavigation().moveTo(livingentity, this.moveSpeedAmp);
					this.attackTime = -5;
				} else {
					if (this.attackTime == 4) {
						this.entity.setAttackingState(statecheck);
						this.entity.getNavigation().moveTo(livingentity, this.moveSpeedAmp);
						this.entity.doHurtTarget(livingentity);
						livingentity.invulnerableTime = 0;
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
		return (double) (this.entity.getBbWidth() * 1.0F * this.entity.getBbWidth() * 1.0F + attackTarget.getBbWidth());
	}
}