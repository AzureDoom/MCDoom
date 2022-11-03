package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class RangedAttackGoal extends Goal {
	private final DemonEntity entity;
	private double moveSpeedAmp = 1;
	private int attackTime = -1;
	private AbstractRangedAttack attack;
	private boolean multiShot;

	public RangedAttackGoal(DemonEntity mob, AbstractRangedAttack attack, double moveSpeedAmpIn) {
		this.entity = mob;
		this.moveSpeedAmp = moveSpeedAmpIn;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		this.attack = attack;
		this.multiShot = false;
	}

	public RangedAttackGoal(DemonEntity mob, AbstractRangedAttack attack, double moveSpeedAmpIn, boolean multishot) {
		this.entity = mob;
		this.moveSpeedAmp = moveSpeedAmpIn;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		this.attack = attack;
		this.multiShot = multishot;
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
				if (this.entity.distanceTo(livingentity) >= 6.0D) {
					if (this.attackTime == 1) {
						this.entity.getNavigation().stop();
						this.entity.setAttackingState(2);
					}
					if (this.attackTime == 4) {
						this.attack.shoot();
					}
					if (this.attackTime == 6 && this.multiShot) {
						this.attack.shoot();

						boolean isInsideWaterBlock = entity.world.isWater(entity.getBlockPos());
						entity.spawnLightSource(this.entity, isInsideWaterBlock);
					}
					if (this.attackTime == 8) {
						this.entity.setAttackingState(0);
						this.attackTime = -15;
						this.entity.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);
					}
				} else {
					this.entity.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);
					if (this.attackTime == 4) {
						this.entity.getNavigation().stop();
						if (d0 <= d1) {
							this.entity.setAttackingState(1);
							this.entity.tryAttack(livingentity);
						}
						livingentity.timeUntilRegen = 0;
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
		return (double) (this.entity.getWidth() * 2.0F * this.entity.getWidth() * 2.0F + attackTarget.getWidth());
	}
}
