package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.item.weapons.PistolItem;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;

public class RangedPistolAttackGoal<T extends HostileEntity & RangedAttackMob> extends Goal {
	private final DemonEntity actor;
	private final double speed;
	private int attackInterval;
	private final float squaredRange;
	private int cooldown = -1;
	private int targetSeeingTicker;
	private boolean movingToLeft;
	private boolean backward;
	private int combatTicks = -1;
	private int statecheck;

	public RangedPistolAttackGoal(DemonEntity actor, double speed, int attackInterval, float range, int state) {
		this.actor = actor;
		this.speed = speed;
		this.attackInterval = attackInterval;
		this.squaredRange = range * range;
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
		return this.actor.isHolding(DoomItems.PISTOL);
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
			double d = this.actor.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
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

			if (d <= (double) this.squaredRange && this.targetSeeingTicker >= 20) {
				this.actor.getNavigation().stop();
				++this.combatTicks;
			} else {
				this.actor.getNavigation().startMovingTo(livingEntity, this.speed);
				this.combatTicks = -1;
			}

			if (this.combatTicks >= 20) {
				if ((double) this.actor.getRandom().nextFloat() < 0.3D) {
					this.movingToLeft = !this.movingToLeft;
				}

				if ((double) this.actor.getRandom().nextFloat() < 0.3D) {
					this.backward = !this.backward;
				}

				this.combatTicks = 0;
			}

			if (this.combatTicks > -1) {
				if (d > (double) (this.squaredRange * 0.75F)) {
					this.backward = false;
				} else if (d < (double) (this.squaredRange * 0.25F)) {
					this.backward = true;
				}

				this.actor.getMoveControl().strafeTo(this.backward ? -0.5F : 0.5F, this.movingToLeft ? 0.5F : -0.5F);
				this.actor.lookAtEntity(livingEntity, 30.0F, 30.0F);
			} else {
				this.actor.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
			}

			if (this.actor.isUsingItem()) {
				if (!bl && this.targetSeeingTicker < -60) {
					this.actor.clearActiveItem();
				} else if (bl) {
					int i = this.actor.getItemUseTime();
					if (i >= 19) {
						this.actor.setAttackingState(statecheck);
					}
					if (i >= 20) {
						this.actor.clearActiveItem();
						((RangedAttackMob) this.actor).attack(livingEntity, PistolItem.getPullProgress(i));
						this.cooldown = this.attackInterval;
					}
				}
			} else if (--this.cooldown <= 0 && this.targetSeeingTicker >= -60) {
				this.actor.setCurrentHand(ProjectileUtil.getHandPossiblyHolding(this.actor, DoomItems.PISTOL));
			}

		}
	}
}