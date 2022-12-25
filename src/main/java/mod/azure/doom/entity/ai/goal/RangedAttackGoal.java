package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class RangedAttackGoal extends Goal {
	private final DemonEntity entity;
	private double moveSpeedAmp = 1;
	private int attackTime = -1;
	private AbstractRangedAttack attack;
	private boolean multiShot;

	public RangedAttackGoal(DemonEntity mob, AbstractRangedAttack attack, double moveSpeedAmpIn) {
		this.entity = mob;
		this.moveSpeedAmp = moveSpeedAmpIn;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		this.attack = attack;
		this.multiShot = false;
	}

	public RangedAttackGoal(DemonEntity mob, AbstractRangedAttack attack, double moveSpeedAmpIn, boolean multishot) {
		this.entity = mob;
		this.moveSpeedAmp = moveSpeedAmpIn;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		this.attack = attack;
		this.multiShot = multishot;
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
			double d0 = this.entity.distanceToSqr(livingentity.getX(), livingentity.getY(),
					livingentity.getZ());
			double d1 = this.getAttackReachSqr(livingentity);
			if (inLineOfSight) {
				if (this.entity.distanceTo(livingentity) >= 6.0D) {
					if (this.attackTime == 1) {
						this.entity.getNavigation().stop();
						this.entity.setAttackingState(2);
					}
					if (this.attackTime == 4) {
						this.attack.shoot();

						boolean isInsideWaterBlock = entity.level.isWaterAt(entity.blockPosition());
						entity.spawnLightSource(this.entity, isInsideWaterBlock);
					}
					if (this.attackTime == 6 && this.multiShot) {
						this.attack.shoot();

						boolean isInsideWaterBlock = entity.level.isWaterAt(entity.blockPosition());
						entity.spawnLightSource(this.entity, isInsideWaterBlock);
					}
					if (this.attackTime >= 8) {
						this.entity.setAttackingState(0);
						this.attackTime = -15;
						this.entity.getNavigation().moveTo(livingentity, this.moveSpeedAmp);
					}
				} else {
					this.entity.getNavigation().moveTo(livingentity, this.moveSpeedAmp);
					this.entity.setSilent(true);
					if (this.attackTime == 4) {
						this.entity.getNavigation().stop();
						if (d0 <= d1) {
							this.entity.doHurtTarget(livingentity);
							this.entity.setAttackingState(1);
						}
						livingentity.invulnerableTime = 0;
					}
					if (this.attackTime >= 8) {
						this.attackTime = -15;
						this.entity.setAttackingState(0);
					}
				}
			}
		}
	}

	protected double getAttackReachSqr(LivingEntity attackTarget) {
		return (double) (this.entity.getBbWidth() * 2.0F * this.entity.getBbWidth() * 2.0F + attackTarget.getBbWidth());
	}
}
