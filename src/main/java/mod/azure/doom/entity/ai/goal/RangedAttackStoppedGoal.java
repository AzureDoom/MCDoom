package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class RangedAttackStoppedGoal extends Goal {
	private final DemonEntity entity;
	private int attackTime = -1;
	private AbstractRangedAttack attack;

	public RangedAttackStoppedGoal(DemonEntity mob, AbstractRangedAttack attack) {
		this.entity = mob;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		this.attack = attack;
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
					this.entity.getNavigation().moveTo(livingentity, 0.75);
				}
			}
		}
	}
}
