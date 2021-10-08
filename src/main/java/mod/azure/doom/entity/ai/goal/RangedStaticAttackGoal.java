package mod.azure.doom.entity.ai.goal;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class RangedStaticAttackGoal extends Goal {

	private final DemonEntity parentEntity;
	public int attackTimer;
	private AbstractRangedAttack attack;
	private int attackCooldown;
	private int visibleTicksDelay = 20;
	private float maxAttackDistance = 20;
	private int seeTime = -1;
	private int statecheck;
	private boolean flying;

	public RangedStaticAttackGoal(DemonEntity mob, AbstractRangedAttack attack, int attackCooldownIn,
			int visibleTicksDelay, float maxAttackDistanceIn, int state, boolean liftup) {
		this.parentEntity = mob;
		this.attackCooldown = attackCooldownIn;
		this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
		this.attack = attack;
		this.visibleTicksDelay = visibleTicksDelay;
		this.statecheck = state;
		this.flying = liftup;
	}

	public boolean canStart() {
		return this.parentEntity.getTarget() != null;
	}

	public void startExecuting() {
		this.attackTimer = 0;
	}

	public void resetTask() {
		this.parentEntity.setAttackingState(0);
	}

	public void tick() {
		if (this.parentEntity.getTarget() != null) {
			LivingEntity livingentity = this.parentEntity.getTarget();
			boolean inLineOfSight = this.parentEntity.getVisibilityCache().canSee(livingentity);
			if (inLineOfSight != this.seeTime > 0) {
				this.seeTime = 0;
			}
			if (inLineOfSight) {
				++this.seeTime;
			} else {
				--this.seeTime;
			}
			if (livingentity.squaredDistanceTo(this.parentEntity) < this.maxAttackDistance
					&& this.seeTime >= this.visibleTicksDelay) {
				this.parentEntity.getLookControl().lookAt(livingentity, 90.0F, 30.0F);
				++this.attackTimer;
				
				if (this.attackTimer == (this.attackCooldown/ 2)) {
					if (this.flying) {
						parentEntity.setNoGravity(true);
						parentEntity.addVelocity(0, (double) 0.2F * 1.3D, 0);
					}
				}

				if (this.attackTimer == this.attackCooldown) {
					attack.shoot();
					this.attackTimer = 0;
				}
			} else if (this.attackTimer > 0) {
				--this.attackTimer;
			}
			this.parentEntity.setAttackingState(attackTimer >= attackCooldown * 0.25 ? statecheck : 0);
		}
	}

}
