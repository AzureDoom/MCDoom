package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.attack.AbstractRangedAttack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class RangedStrafeAttackGoal extends Goal {
	private final DemonEntity entity;
	private double moveSpeedAmp = 1;
	private int attackCooldown;
	private int visibleTicksDelay = 20;
	private float maxAttackDistance = 20;
	private int strafeTicks = 20;
	private int attackTime = -1;
	private int seeTime;
	private boolean strafingClockwise;
	private boolean strafingBackwards;
	private int strafingTime = -1;
	private int statecheck;

	private AbstractRangedAttack attack;

	public RangedStrafeAttackGoal(DemonEntity mob, AbstractRangedAttack attack, double moveSpeedAmpIn,
			int attackCooldownIn, int visibleTicksDelay, int strafeTicks, float maxAttackDistanceIn, int state) {
		this.entity = mob;
		this.moveSpeedAmp = moveSpeedAmpIn;
		this.attackCooldown = attackCooldownIn;
		this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		this.attack = attack;
		this.visibleTicksDelay = visibleTicksDelay;
		this.strafeTicks = strafeTicks;
		this.statecheck = state;
	}

	// use defaults
	public RangedStrafeAttackGoal(DemonEntity mob, AbstractRangedAttack attack, int attackCooldownIn) {
		this.entity = mob;
		this.attackCooldown = attackCooldownIn;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		this.attack = attack;
	}

	private boolean multiShot = false;
	private int multiShotCount = 0;
	private int multiShotTickDelay = 0;

	private boolean multiShooting = false;
	private int multiShotsLeft = 0;
	private int multiShotTicker = 0;

	public RangedStrafeAttackGoal setMultiShot(int count, int tickDelay) {
		multiShot = true;
		multiShotCount = count;
		multiShotTickDelay = tickDelay;
		return this;
	}

	public boolean tickMultiShot() {
		if (multiShotsLeft > 0 && multiShotTicker == 0) {
			multiShotsLeft--;
			if (multiShotsLeft == 0)
				finishMultiShot();
			multiShotTicker = multiShotTickDelay;
			return true;
		}
		multiShotTicker--;
		return false;
	}

	public void beginMultiShooting() {
		multiShooting = true;
		multiShotsLeft = multiShotCount - 1;
		multiShotTicker = multiShotTickDelay;
	}

	public void finishMultiShot() {
		multiShooting = false;
		multiShotsLeft = 0;
	}

	public void setAttackInterval(int attackCooldownIn) {
		this.attackCooldown = attackCooldownIn;
	}
	
	public boolean canStart() {
		return this.entity.getTarget() != null;
	}

	/**
	 * Returns whether execution should begin. You can also read and cache any state
	 * necessary for execution in this method as well.
	 */
	public boolean shouldExecute() {
		return this.entity.getTarget() != null;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldKeepRunning() {
		return (this.shouldExecute() || !this.entity.getNavigation().isIdle());
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void run() {
		super.start();
		this.entity.setAttacking(true);
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by
	 * another one
	 */
	public void finishRunning() {
		super.canStop();
		this.entity.setAttacking(false);
		this.seeTime = 0;
		this.attackTime = -1;
		this.entity.clearActiveItem();
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void tick() {
		LivingEntity livingentity = this.entity.getTarget();
		if (livingentity != null) {
			double distanceToTargetSq = this.entity.squaredDistanceTo(livingentity.getX(), livingentity.getY(),
					livingentity.getZ());
			boolean inLineOfSight = this.entity.getVisibilityCache().canSee(livingentity);
			if (inLineOfSight != this.seeTime > 0) {
				this.seeTime = 0;
			}

			if (inLineOfSight) {
				++this.seeTime;
			} else {
				if (multiShot)
					finishMultiShot();
				--this.seeTime;
			}

			if (distanceToTargetSq <= (double) this.maxAttackDistance && this.seeTime >= 20) {
				this.entity.getNavigation().stop();
				++this.strafingTime;
			} else {
				this.entity.getNavigation().startMovingTo(livingentity, this.moveSpeedAmp);
				this.strafingTime = -1;
			}

			if (this.strafingTime >= strafeTicks) {
				if ((double) this.entity.getRandom().nextFloat() < 0.3D) {
					this.strafingClockwise = !this.strafingClockwise;
				}

				if ((double) this.entity.getRandom().nextFloat() < 0.3D) {
					this.strafingBackwards = !this.strafingBackwards;
				}

				this.strafingTime = 0;
			}

			if (this.strafingTime > -1) {
				if (distanceToTargetSq > (double) (this.maxAttackDistance * 0.75F)) {
					this.strafingBackwards = false;
				} else if (distanceToTargetSq < (double) (this.maxAttackDistance * 0.25F)) {
					this.strafingBackwards = true;
				}

				this.entity.getMoveControl().strafeTo(this.strafingBackwards ? -0.5F : 0.5F,
						this.strafingClockwise ? 0.5F : -0.5F);
				this.entity.lookAtEntity(livingentity, 30.0F, 30.0F);
			} else {
				this.entity.getLookControl().lookAt(livingentity, 30.0F, 30.0F);
			}

			// attack
			if (multiShooting) {
				if (tickMultiShot())
					this.attack.shoot();
				return;
			}

			if (this.seeTime >= this.visibleTicksDelay) {
				if (this.attackTime >= this.attackCooldown) {
					this.attack.shoot();
					this.attackTime = 0;
				} else
					this.attackTime++;
			}
			this.entity.setAttackingState(attackTime >= attackCooldown * 0.75 ? this.statecheck : 0);
		}
	}
}
