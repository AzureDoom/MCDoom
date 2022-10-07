package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import mod.azure.doom.entity.tierheavy.PainEntity;
import mod.azure.doom.util.registry.DoomEntities;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class PainAttackGoal extends Goal {
	private final PainEntity entity;
	private int attackTime = -1;

	public PainAttackGoal(PainEntity mob) {
		this.entity = mob;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
	}

	@Override
	public boolean canUse() {
		return this.entity.getTarget() != null;
	}

	@Override
	public void start() {
		super.start();
		this.attackTime = 0;
		this.entity.setAggressive(true);
	}

	@Override
	public void stop() {
		super.stop();
		this.entity.setAggressive(false);
		this.entity.setAttackingState(0);
		this.attackTime = -1;
	}

	@Override
	public void tick() {
		LivingEntity livingentity = this.entity.getTarget();
		if (livingentity != null) {
			boolean inLineOfSight = this.entity.getSensing().hasLineOfSight(livingentity);
			this.attackTime++;
			this.entity.lookAt(livingentity, 30.0F, 30.0F);
			double d0 = this.entity.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
			double d1 = (double) (this.entity.getBbWidth() * 2.0F * this.entity.getBbWidth() * 2.0F
					+ entity.getBbWidth());
			if (inLineOfSight) {
				if (this.entity.distanceTo(livingentity) >= 12.0D) {
					this.entity.getNavigation().moveTo(livingentity, 1);
					this.entity.getLookControl().setLookAt(livingentity.getX(), livingentity.getEyeY(),
							livingentity.getZ());
					if (this.attackTime == 1) {
						this.entity.setAttackingState(0);
					}
					if (this.attackTime == 12) {
						this.entity.setAttackingState(1);
						entity.playSound(DoomSounds.PAIN_HURT.get(), 1.0F, 1.0F);
						if (this.entity.getVariant() == 1 || this.entity.getVariant() == 3) {
							LostSoulEntity lost_soul = DoomEntities.LOST_SOUL.get().create(entity.level);
							lost_soul.moveTo(this.entity.getX(), this.entity.getY(), this.entity.getZ(), 0, 0);
							entity.level.addFreshEntity(lost_soul);
						} else {
							LostSoulEntity lost_soul = DoomEntities.LOST_SOUL.get().create(entity.level);
							lost_soul.moveTo(this.entity.getX(), this.entity.getY(), this.entity.getZ(), 0, 0);
							entity.level.addFreshEntity(lost_soul);

							LostSoulEntity lost_soul1 = DoomEntities.LOST_SOUL.get().create(entity.level);
							lost_soul1.moveTo(this.entity.getX(), this.entity.getY(), this.entity.getZ(), 0, 0);
							entity.level.addFreshEntity(lost_soul1);
						}

						boolean isInsideWaterBlock = entity.level.isWaterAt(entity.blockPosition());
						entity.spawnLightSource(this.entity, isInsideWaterBlock);
					}
					if (this.attackTime == 20) {
						this.attackTime = 0;
						this.entity.setAttackingState(0);
					}
				} else {
					this.entity.getLookControl().setLookAt(livingentity.getX(), livingentity.getEyeY(),
							livingentity.getZ());
					if (this.attackTime == 3) {
						if (d0 <= d1) {
							this.entity.setAttackingState(1);
							this.entity.doHurtTarget(livingentity);
						}
					}
					if (this.attackTime == 20) {
						this.attackTime = 0;
						this.entity.setAttackingState(0);
					}
				}
			}
		}
	}
}
