package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class RangedAttackStoppedGoal extends Goal {
	private final DemonEntity entity;
	private int attackTime = -1;
	private AbstractRangedAttack attack;

	public RangedAttackStoppedGoal(DemonEntity mob, AbstractRangedAttack attack) {
		this.entity = mob;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		this.attack = attack;
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
			if (inLineOfSight) {
				if (this.attackTime == 1) {
					this.entity.getNavigation().stop();
					this.entity.setAttackingState(1);
				}
				if (this.attackTime == 4) {
					this.attack.shoot();
				}
				if (this.attackTime == 8) {
					this.entity.setAttackingState(0);
					this.attackTime = -5;
					this.entity.getNavigation().startMovingTo(livingentity, 0.75);
				}
			}
		}
	}
}
