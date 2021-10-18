package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.item.weapons.Chaingun;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.projectile.ProjectileHelper;

public class RangedChaingunAttackGoal<T extends CreatureEntity & IRangedAttackMob> extends Goal {
	private final DemonEntity entity;
	private final double moveSpeedAmp;
	private int attackCooldown;
	private final float maxAttackDistance;
	private int attackTime = -1;
	private int seeTime;
	private boolean strafingClockwise;
	private boolean strafingBackwards;
	private int strafingTime = -1;
	private int statecheck;

	public RangedChaingunAttackGoal(DemonEntity mob, double moveSpeedAmpIn, int attackCooldownIn, float maxAttackDistanceIn, int state) {
		this.entity = mob;
		this.moveSpeedAmp = moveSpeedAmpIn;
		this.attackCooldown = attackCooldownIn;
		this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		this.statecheck = state;
	}

	public void setAttackCooldown(int attackCooldownIn) {
		this.attackCooldown = attackCooldownIn;
	}

	public boolean canUse() {
		return this.entity.getTarget() == null ? false : this.isBowInMainhand();
	}

	protected boolean isBowInMainhand() {
		return this.entity.getMainHandItem().getItem() instanceof Chaingun
				|| this.entity.getOffhandItem().getItem() instanceof Chaingun;
	}

	public boolean canContinueToUse() {
		return (this.canUse() || !this.entity.getNavigation().isDone()) && this.isBowInMainhand();
	}

	public void start() {
		super.start();
		this.entity.setAggressive(true);
		this.entity.setAttackingState(0);
	}

	public void stop() {
		super.stop();
		this.entity.setAggressive(false);
		this.seeTime = 0;
		this.attackTime = -1;
		this.entity.stopUsingItem();
		this.entity.setAttackingState(0);
	}

	public void tick() {
		LivingEntity livingentity = this.entity.getTarget();
		if (livingentity != null) {
			double d0 = this.entity.distanceToSqr(livingentity.getX(), livingentity.getY(),
					livingentity.getZ());
			boolean flag = this.entity.getSensing().canSee(livingentity);
			boolean flag1 = this.seeTime > 0;
			if (flag != flag1) {
				this.seeTime = 0;
			}

			if (flag) {
				++this.seeTime;
			} else {
				--this.seeTime;
			}

			if (!(d0 > (double) this.maxAttackDistance) && this.seeTime >= 20) {
				this.entity.getNavigation().stop();
				++this.strafingTime;
			} else {
				this.entity.getNavigation().moveTo(livingentity, this.moveSpeedAmp);
				this.strafingTime = -1;
			}

			if (this.strafingTime >= 20) {
				if ((double) this.entity.getRandom().nextFloat() < 0.3D) {
					this.strafingClockwise = !this.strafingClockwise;
				}

				if ((double) this.entity.getRandom().nextFloat() < 0.3D) {
					this.strafingBackwards = !this.strafingBackwards;
				}

				this.strafingTime = 0;
			}

			if (this.strafingTime > -1) {
				if (d0 > (double) (this.maxAttackDistance * 0.75F)) {
					this.strafingBackwards = false;
				} else if (d0 < (double) (this.maxAttackDistance * 0.25F)) {
					this.strafingBackwards = true;
				}

				this.entity.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F,
						this.strafingClockwise ? 0.5F : -0.5F);
				this.entity.lookAt(livingentity, 30.0F, 30.0F);
			} else {
				this.entity.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
			}

			if (this.entity.isUsingItem()) {
				if (!flag && this.seeTime < -60) {
					this.entity.stopUsingItem();
				} else if (flag) {
					int i = this.entity.getTicksUsingItem();
					if (i >= 19) {
						this.entity.setAttackingState(statecheck);
					}
					if (i >= 20) {
						this.entity.stopUsingItem();
						((IRangedAttackMob) this.entity).performRangedAttack(livingentity,
								Chaingun.getArrowVelocity(i));
						this.attackTime = this.attackCooldown;
					}
				}
			} else if (--this.attackTime <= 0 && this.seeTime >= -60) {
				this.entity.startUsingItem(ProjectileHelper.getWeaponHoldingHand(this.entity, DoomItems.CHAINGUN.get()));
			}

		}
	}
}