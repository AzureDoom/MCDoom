package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class KnockbackGoal extends Goal {
	private final DemonEntity entity;
	private double moveSpeedAmp = 1;
	private int attackTime = -1;

	public KnockbackGoal(DemonEntity mob, double moveSpeedAmpIn) {
		this.entity = mob;
		this.moveSpeedAmp = moveSpeedAmpIn;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
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
			this.entity.getNavigation().moveTo(livingentity, this.moveSpeedAmp);
			if (inLineOfSight && this.entity.getAttckingState() != 1) {
				if (this.attackTime == 1) {
					this.entity.setAttackingState(0);
				}
				if (this.attackTime == 4) {
					for (int i = 1; i < 5; ++i) {
						float f1 = (float) Mth.atan2(livingentity.getZ() - entity.getZ(),
								livingentity.getX() - entity.getX()) + (float) i * (float) Math.PI * 0.4F;
						for (int y = 0; y < 5; ++y) {
							((ArchMakyrEntity) entity).spawnFlames(
									livingentity.getX()
											+ (double) Mth.cos(f1) * livingentity.getRandom().nextDouble() * 1.5D,
									livingentity.getZ()
											+ (double) Mth.sin(f1) * livingentity.getRandom().nextDouble() * 1.5D,
									Math.min(livingentity.getY(), livingentity.getY()),
									Math.max(livingentity.getY(), livingentity.getY()) + 1.0D, f1, 0);
						}
					}
					livingentity.setDeltaMovement(livingentity.getDeltaMovement().multiply(0.4f, 1.4f, 0.4f));
					this.entity.setAttackingState(2);
				}
				if (this.attackTime == 8) {
					this.entity.setAttackingState(0);
				}
				if (this.attackTime == 25) {
					this.attackTime = -25;
				}
			}
		}
	}

	protected double getAttackReachSqr(LivingEntity attackTarget) {
		return (double) (this.entity.getBbWidth() * 2.0F * this.entity.getBbWidth() * 2.0F + attackTarget.getBbWidth());
	}
}
