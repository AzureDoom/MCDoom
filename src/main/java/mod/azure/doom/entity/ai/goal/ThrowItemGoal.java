package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import javax.annotation.Nullable;

import mod.azure.doom.entity.DemonEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class ThrowItemGoal extends Goal {
	private final Mob mob;
	private final DemonEntity rangedAttackMob;
	@Nullable
	private LivingEntity target;
	private int attackTime = -1;
	private final double speedModifier;

	public ThrowItemGoal(DemonEntity mob, double mobSpeed) {
		this.rangedAttackMob = mob;
		this.mob = (Mob) mob;
		this.speedModifier = mobSpeed;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	public boolean canUse() {
		LivingEntity livingentity = this.mob.getTarget();
		if (livingentity != null && livingentity.isAlive()) {
			this.target = livingentity;
			return true;
		} else {
			return false;
		}
	}

	public boolean canContinueToUse() {
		return this.canUse() || !this.mob.getNavigation().isDone();
	}

	public void start() {
		super.start();
		this.rangedAttackMob.setAggressive(true);
	}

	public void stop() {
		super.stop();
		this.rangedAttackMob.setAggressive(false);
		this.rangedAttackMob.setAttackingState(0);
		this.attackTime = -1;
	}

	public void tick() {
		LivingEntity livingentity = this.rangedAttackMob.getTarget();
		if (livingentity != null) {
			boolean inLineOfSight = this.rangedAttackMob.getSensing().hasLineOfSight(livingentity);
			double d0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
			this.attackTime++;
			this.rangedAttackMob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
			if (inLineOfSight) {
				if (this.rangedAttackMob.distanceTo(livingentity) >= 5.0D) {
					this.rangedAttackMob.getNavigation().moveTo(livingentity, this.speedModifier);
					if (this.attackTime == 4) {
						this.rangedAttackMob.setAttackingState(1);
						this.rangedAttackMob.getNavigation().stop();
						float f = (float) Math.sqrt(d0) / 100.0F;
						float f1 = Mth.clamp(f, 0.1F, 100.0F);
						this.rangedAttackMob.performRangedAttack(this.target, f1);
						livingentity.invulnerableTime = 0;
					}
					if (this.attackTime == 8) {
						this.attackTime = -15;
						this.rangedAttackMob.setAttackingState(0);
					}
				} else {
					this.rangedAttackMob.getNavigation().moveTo(livingentity, this.speedModifier);
					this.rangedAttackMob.setSilent(true);
					if (this.attackTime == 4) {
						this.rangedAttackMob.setAttackingState(1);
						this.rangedAttackMob.getNavigation().stop();
						this.rangedAttackMob.doHurtTarget(livingentity);
						livingentity.invulnerableTime = 0;
					}
					if (this.attackTime == 8) {
						this.attackTime = -15;
						this.rangedAttackMob.setAttackingState(0);
					}
				}
			}
		}
	}

	protected double getAttackReachSqr(LivingEntity attackTarget) {
		return (double) (this.rangedAttackMob.getBbWidth() * 1.25F * this.rangedAttackMob.getBbWidth() * 1.25F + attackTarget.getBbWidth());
	}
}