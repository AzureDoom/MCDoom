package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.item.weapons.Shotgun;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;

public class RangedShotgunAttackGoal<T extends HostileEntity & RangedAttackMob> extends Goal {
	private final DemonEntity actor;
	private final double speed;
	private int attackInterval;
	private int cooldown = -1;
	private int targetSeeingTicker;
	private int statecheck;

	public RangedShotgunAttackGoal(DemonEntity actor, double speed, int attackInterval, float range, int state) {
		this.actor = actor;
		this.speed = speed;
		this.attackInterval = attackInterval;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		this.statecheck = state;
	}

	public void setAttackInterval(int attackInterval) {
		this.attackInterval = attackInterval;
	}

	public boolean canStart() {
		return this.actor.getTarget() == null ? false : this.isHoldingBow();
	}

	protected boolean isHoldingBow() {
		return this.actor.isHolding(DoomItems.SG);
	}

	public boolean shouldContinue() {
		return (this.canStart() || !this.actor.getNavigation().isIdle()) && this.isHoldingBow();
	}

	public void start() {
		super.start();
		this.actor.setAttacking(true);
		this.actor.setAttackingState(0);
	}

	public void stop() {
		super.stop();
		this.actor.setAttacking(false);
		this.targetSeeingTicker = 0;
		this.cooldown = -1;
		this.actor.clearActiveItem();
		this.actor.setAttackingState(0);
	}

	public void tick() {
		LivingEntity livingEntity = this.actor.getTarget();
		if (livingEntity != null) {
			boolean bl = this.actor.getVisibilityCache().canSee(livingEntity);
			boolean bl2 = this.targetSeeingTicker > 0;
			if (bl != bl2) {
				this.targetSeeingTicker = 0;
			}

			if (bl) {
				++this.targetSeeingTicker;
			} else {
				--this.targetSeeingTicker;
			}

			this.actor.getNavigation().startMovingTo(livingEntity, this.speed);
			this.actor.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
			double d0 = this.actor.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());

			if (this.actor.isUsingItem() && this.actor.distanceTo(livingEntity) >= 6.0D) {
				if (!bl && this.targetSeeingTicker < -60) {
					this.actor.clearActiveItem();
				} else if (bl) {
					int i = this.actor.getItemUseTime();
					if (i >= 19) {
						this.actor.setAttackingState(statecheck);
					}
					if (i >= 20) {
						this.actor.clearActiveItem();
						((RangedAttackMob) this.actor).attack(livingEntity, Shotgun.getPullProgress(i));
						this.cooldown = this.attackInterval;
					}
				}
			} else if (--this.cooldown <= 0 && this.targetSeeingTicker >= -60) {
				this.attack(livingEntity, d0);
				this.actor.setCurrentHand(ProjectileUtil.getHandPossiblyHolding(this.actor, DoomItems.SG));
			}

		}
	}
	
	protected void attack(LivingEntity livingentity, double squaredDistance) {
		double d0 = this.getSquaredMaxAttackDistance(livingentity);
		if (squaredDistance <= d0) {
			this.cooldown = 20;
			this.actor.setAttackingState(1);
			this.actor.tryAttack(livingentity);
		}
	}
	
	protected double getSquaredMaxAttackDistance(LivingEntity entity) {
		return (double) (this.actor.getWidth() * 1.0F * this.actor.getWidth() * 1.0F + entity.getWidth());
	}
}