package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import javax.annotation.Nullable;

import mod.azure.doom.entity.DemonEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;

public class ThrowItemGoal extends Goal {
	private final DemonEntity rangedAttackMob;
	@Nullable
	private LivingEntity target;
	private int updateCountdownTicks = -1;
	private final double mobSpeed;

	public ThrowItemGoal(DemonEntity mob, double mobSpeed) {
		this.rangedAttackMob = mob;
		this.mobSpeed = mobSpeed;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
	}

	@Override
	public boolean canStart() {
		LivingEntity livingentity = this.rangedAttackMob.getTarget();
		if (livingentity != null && livingentity.isAlive()) {
			this.target = livingentity;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean shouldContinue() {
		return this.canStart() || !this.rangedAttackMob.getNavigation().isIdle();
	}

	public void start() {
		super.start();
		this.rangedAttackMob.setAttacking(true);
	}

	public void stop() {
		super.stop();
		this.rangedAttackMob.setAttacking(false);
		this.rangedAttackMob.setAttackingState(0);
		this.updateCountdownTicks = -1;
	}

	public boolean requiresUpdateEveryTick() {
		return true;
	}

	public void tick() {

		LivingEntity livingentity = this.rangedAttackMob.getTarget();
		if (livingentity != null) {
			boolean inLineOfSight = this.rangedAttackMob.getVisibilityCache().canSee(livingentity);
			double d0 = this.rangedAttackMob.squaredDistanceTo(this.target.getX(), this.target.getY(),
					this.target.getZ());
			this.updateCountdownTicks++;
			this.rangedAttackMob.getLookControl().lookAt(this.target, 30.0F, 30.0F);
			if (inLineOfSight) {
				if (this.rangedAttackMob.distanceTo(livingentity) >= 5.0D) {
					this.rangedAttackMob.getNavigation().startMovingTo(livingentity, this.mobSpeed);
					if (this.updateCountdownTicks == 4) {
						this.rangedAttackMob.setAttackingState(1);
						this.rangedAttackMob.getNavigation().stop();
						float f = (float) Math.sqrt(d0) / 100.0F;
						float f1 = MathHelper.clamp(f, 0.1F, 100.0F);
						this.rangedAttackMob.attack(this.target, f1);
						livingentity.timeUntilRegen = 0;
					}
					if (this.updateCountdownTicks == 8) {
						this.updateCountdownTicks = -15;
						this.rangedAttackMob.setAttackingState(0);
					}
				} else {
					this.rangedAttackMob.getNavigation().startMovingTo(livingentity, this.mobSpeed);
					if (this.updateCountdownTicks == 4) {
						this.rangedAttackMob.setAttackingState(1);
						this.rangedAttackMob.getNavigation().stop();
						this.rangedAttackMob.tryAttack(livingentity);
						livingentity.timeUntilRegen = 0;
					}
					if (this.updateCountdownTicks == 8) {
						this.updateCountdownTicks = -15;
						this.rangedAttackMob.setAttackingState(0);
					}
				}
			}
		}
	}

	protected double getAttackReachSqr(LivingEntity attackTarget) {
		return (double) (this.rangedAttackMob.getWidth() * 1.25F * this.rangedAttackMob.getWidth() * 1.25F
				+ attackTarget.getWidth());
	}
}