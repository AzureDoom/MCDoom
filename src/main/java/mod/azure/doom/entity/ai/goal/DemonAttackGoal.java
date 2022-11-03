package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.DemonEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.Box;

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
			final Box aabb2 = new Box(this.entity.getBlockPos()).expand(2D);
			if (inLineOfSight) {
				this.entity.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);
				if (this.attackTime == 1) {
					this.entity.setAttackingState(statecheck);
				}
				if (this.attackTime == 4) {
					this.entity.getCommandSenderWorld().getOtherEntities(this.entity, aabb2).forEach(e -> {
						if ((e instanceof LivingEntity)) {
							this.entity.tryAttack(livingentity);
							livingentity.timeUntilRegen = 0;
						}
					});
				}
				if (this.attackTime >= 8) {
					this.attackTime = -5;
					this.entity.setAttackingState(0);
				}
			}
		}
	}

}