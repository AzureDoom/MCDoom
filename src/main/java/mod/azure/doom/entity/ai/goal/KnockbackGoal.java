package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.MathHelper;

public class KnockbackGoal extends Goal {
	private final DemonEntity entity;
	protected double moveSpeedAmp = 1;
	private int attackTime = -1;

	public KnockbackGoal(DemonEntity mob, double moveSpeedAmpIn) {
		this.entity = mob;
		this.moveSpeedAmp = moveSpeedAmpIn;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
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
			if (inLineOfSight && this.entity.getAttckingState() != 1) {
				if (this.attackTime == 1) {
					this.entity.setAttackingState(0);
				}
				if (this.attackTime == 4) {
					for (int i = 1; i < 5; ++i) {
						float f1 = (float) MathHelper.atan2(livingentity.getZ() - entity.getZ(),
								livingentity.getX() - entity.getX()) + (float) i * (float) Math.PI * 0.4F;
						for (int y = 0; y < 5; ++y) {
							((ArchMakyrEntity) entity).spawnFlames(
									livingentity.getX() + (double) MathHelper.cos(f1)
											* livingentity.getRandom().nextDouble() * 1.5D,
									livingentity.getZ() + (double) MathHelper.sin(f1)
											* livingentity.getRandom().nextDouble() * 1.5D,
									Math.min(livingentity.getY(), livingentity.getY()),
									Math.max(livingentity.getY(), livingentity.getY()) + 1.0D, f1, 0);
						}
					}
					livingentity.setVelocity(livingentity.getVelocity().add(0.4f, 1.4f, 0.4f));
					this.entity.setAttackingState(2);
				}
				if (this.attackTime == 8) {
					this.entity.setAttackingState(0);
				}
				if (this.attackTime >= 25) {
					this.attackTime = -25;
				}
			}
		}
	}

	protected double getAttackReachSqr(LivingEntity attackTarget) {
		return (double) (this.entity.getWidth() * 2.0F * this.entity.getWidth() * 2.0F + attackTarget.getWidth());
	}
}
