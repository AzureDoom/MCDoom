package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import mod.azure.doom.entity.tierheavy.PainEntity;
import mod.azure.doom.util.registry.DoomEntities;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.AABB;

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
	public boolean canContinueToUse() {
		return this.canUse();
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
			final AABB aabb = new AABB(this.entity.blockPosition()).inflate(64D);
			final AABB aabb2 = new AABB(this.entity.blockPosition()).inflate(3D);
			if (inLineOfSight) {
				if (this.entity.distanceTo(livingentity) >= 3.0D) {
					this.entity.getNavigation().moveTo(livingentity, 1);
					int i = this.entity.level.getEntities(DoomEntities.LOST_SOUL.get(), aabb, Entity::isAlive).size();
					if (i <= 15) {
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
						if (this.attackTime >= 20) {
							this.attackTime = 0;
							this.entity.setAttackingState(0);
						}
					} else {
						--this.attackTime;
						this.entity.setAttackingState(0);
					}
				} else {
					if (this.attackTime == 3) {
						this.entity.getCommandSenderWorld().getEntities(this.entity, aabb2).forEach(e -> {
							if ((e instanceof LivingEntity)) {
								this.entity.doHurtTarget(livingentity);
							}
						});
					}
					if (this.attackTime >= 10) {
						this.attackTime = 0;
						this.entity.setAttackingState(0);
					}
				}
			}
		}
	}
}
