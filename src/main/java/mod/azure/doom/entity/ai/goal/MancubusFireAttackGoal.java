package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.projectiles.entity.BarenBlastEntity;
import mod.azure.doom.entity.tierheavy.MancubusEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class MancubusFireAttackGoal extends Goal {
	private final MancubusEntity entity;
	private int attackTime = -1;
	private AbstractRangedAttack attack;

	public MancubusFireAttackGoal(MancubusEntity mob, AbstractRangedAttack attack) {
		this.entity = mob;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		this.attack = attack;
	}

	@Override
	public boolean canUse() {
		return this.entity.getTarget() != null;
	}

	@Override
	public void start() {
		super.start();
		this.entity.setAggressive(true);
		this.entity.getNavigation().stop();
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
			Level world = this.entity.level;
			Vec3 vector3d = this.entity.getViewVector(1.0F);
			double d = Math.min(livingentity.getY(), entity.getY());
			double e1 = Math.max(livingentity.getY(), entity.getY()) + 1.0D;
			float f2 = (float) Mth.atan2(livingentity.getZ() - entity.getZ(), livingentity.getX() - entity.getX());
			int j;
			double f = livingentity.getX() - (this.entity.getX() + vector3d.x * 2.0D);
			double g = livingentity.getY(0.5D) - (0.5D + this.entity.getY(0.5D));
			double h = livingentity.getZ() - (this.entity.getZ() + vector3d.z * 2.0D);
			BarenBlastEntity fireballEntity = new BarenBlastEntity(entity.level, this.entity, f, g, h, 6);
			if (inLineOfSight) {
				if (entity.distanceTo(livingentity) < 3.0D) {
					this.entity.getNavigation().stop();
					this.entity.getLookControl().setLookAt(livingentity.getX(), livingentity.getEyeY(),
							livingentity.getZ());
					if (this.attackTime == 1) {
						this.entity.setAttackingState(0);
					}
					if (this.attackTime == 4) {
						for (j = 0; j < 5; ++j) {
							float h2 = f2 + (float) j * (float) Math.PI * 0.4F;
							entity.spawnFlames(entity.getX() + (double) Mth.cos(h2) * 1.5D,
									entity.getZ() + (double) Mth.sin(h2) * 1.5D, d, e1, h2, 0);
						}

						boolean isInsideWaterBlock = entity.level.isWaterAt(entity.blockPosition());
						entity.spawnLightSource(this.entity, isInsideWaterBlock);
						this.entity.setAttackingState(3);
					}
					if (this.attackTime == 8) {
						this.attackTime = -15;
						this.entity.setAttackingState(0);
					}
				} else if (entity.distanceTo(livingentity) < 13.0D && entity.distanceTo(livingentity) > 3.0D) {
					this.entity.getNavigation().stop();
					this.entity.getLookControl().setLookAt(livingentity.getX(), livingentity.getEyeY(),
							livingentity.getZ());
					if (this.attackTime == 1) {
						this.entity.setAttackingState(0);
					}
					if (this.attackTime == 4) {
						entity.playSound(SoundEvents.FIRE_AMBIENT, 1.0F, 1.0F);
						this.attack.shoot();

						boolean isInsideWaterBlock = entity.level.isWaterAt(entity.blockPosition());
						entity.spawnLightSource(this.entity, isInsideWaterBlock);
						this.entity.setAttackingState(2);
					}
					if (this.attackTime == 8) {
						this.attackTime = -15;
						this.entity.setAttackingState(0);
					}
				} else {
					this.entity.getNavigation().stop();
					this.entity.getLookControl().setLookAt(livingentity.getX(), livingentity.getEyeY(),
							livingentity.getZ());
					if (this.attackTime == 1) {
						this.entity.setAttackingState(0);
					}
					if (this.attackTime == 4) {
						fireballEntity.setPos(this.entity.getX() + vector3d.x * 2.0D, this.entity.getY(0.5D) + 0.5D,
								entity.getZ() + vector3d.z * 2.0D);
						world.addFreshEntity(fireballEntity);

						boolean isInsideWaterBlock = entity.level.isWaterAt(entity.blockPosition());
						entity.spawnLightSource(this.entity, isInsideWaterBlock);
						this.entity.setAttackingState(1);
					}
					if (this.attackTime == 8) {
						this.attackTime = -15;
						this.entity.setAttackingState(0);
						this.entity.getNavigation().moveTo(livingentity, 1.0);
					}
				}
			}
		}
	}
}
