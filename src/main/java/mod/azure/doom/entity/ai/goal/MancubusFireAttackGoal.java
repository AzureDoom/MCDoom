package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.attack.AbstractRangedAttack;
import mod.azure.doom.entity.projectiles.entity.BarenBlastEntity;
import mod.azure.doom.entity.tierheavy.MancubusEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MancubusFireAttackGoal extends Goal {
	private final MancubusEntity entity;
	private int attackTime = -1;
	private AbstractRangedAttack attack;

	public MancubusFireAttackGoal(MancubusEntity mob, AbstractRangedAttack attack) {
		this.entity = mob;
		this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		this.attack = attack;
	}

	@Override
	public boolean canStart() {
		return this.entity.getTarget() != null;
	}

	@Override
	public boolean shouldContinue() {
		return this.canStart();
	}

	@Override
	public void start() {
		super.start();
		this.attackTime = 0;
		this.entity.setAttacking(true);
		this.entity.getNavigation().stop();
	}

	@Override
	public void stop() {
		super.stop();
		this.entity.setAttacking(false);
		this.entity.setAttackingState(0);
		this.attackTime = -1;
	}

	@Override
	public void tick() {
		LivingEntity livingentity = this.entity.getTarget();
		if (livingentity != null) {
			boolean inLineOfSight = this.entity.getVisibilityCache().canSee(livingentity);
			this.attackTime++;
			this.entity.lookAtEntity(livingentity, 30.0F, 30.0F);
			World world = this.entity.world;
			Vec3d vec3d = this.entity.getRotationVec(1.0F);
			double d = Math.min(livingentity.getY(), entity.getY());
			double e1 = Math.max(livingentity.getY(), entity.getY()) + 1.0D;
			float f2 = (float) MathHelper.atan2(livingentity.getZ() - entity.getZ(),
					livingentity.getX() - entity.getX());
			int j;
			double f = livingentity.getX() - (this.entity.getX() + vec3d.x * 2.0D);
			double g = livingentity.getBodyY(0.5D) - (0.5D + this.entity.getBodyY(0.5D));
			double h = livingentity.getZ() - (this.entity.getZ() + vec3d.z * 2.0D);
			BarenBlastEntity fireballEntity = new BarenBlastEntity(entity.world, this.entity, f, g, h, 6);
			if (inLineOfSight) {
				if (entity.distanceTo(livingentity) < 3.0D) {
					this.entity.getNavigation().stop();
					this.entity.getLookControl().lookAt(livingentity.getX(), livingentity.getEyeY(),
							livingentity.getZ());
					if (this.attackTime == 1) {
						this.entity.setAttackingState(0);
					}
					if (this.attackTime == 4) {
						for (j = 0; j < 5; ++j) {
							float h2 = f2 + (float) j * (float) Math.PI * 0.4F;
							entity.spawnFlames(entity.getX() + (double) MathHelper.cos(h2) * 1.5D,
									entity.getZ() + (double) MathHelper.sin(h2) * 1.5D, d, e1, h2, 0);
						}
						this.entity.setAttackingState(3);
					}
					if (this.attackTime == 8) {
						this.attackTime = -15;
						this.entity.setAttackingState(0);
					}
				} else if (entity.distanceTo(livingentity) < 13.0D && entity.distanceTo(livingentity) > 3.0D) {
					this.entity.getNavigation().stop();
					this.entity.getLookControl().lookAt(livingentity.getX(), livingentity.getEyeY(),
							livingentity.getZ());
					if (this.attackTime == 1) {
						this.entity.setAttackingState(0);
					}
					if (this.attackTime == 4) {
						entity.playSound(SoundEvents.BLOCK_FIRE_AMBIENT, 1.0F, 1.0F);
						this.attack.shoot();
						this.entity.setAttackingState(2);
					}
					if (this.attackTime == 8) {
						this.attackTime = -15;
						this.entity.setAttackingState(0);
					}
				} else {
					this.entity.getNavigation().stop();
					this.entity.getLookControl().lookAt(livingentity.getX(), livingentity.getEyeY(),
							livingentity.getZ());
					if (this.attackTime == 1) {
						this.entity.setAttackingState(0);
					}
					if (this.attackTime == 4) {
						fireballEntity.updatePosition(this.entity.getX() + vec3d.x * 2.0D,
								this.entity.getBodyY(0.5D) + 0.5D, entity.getZ() + vec3d.z * 2.0D);
						world.spawnEntity(fireballEntity);
						this.entity.setAttackingState(1);
					}
					if (this.attackTime == 8) {
						this.attackTime = -15;
						this.entity.setAttackingState(0);
						this.entity.getNavigation().startMovingTo(livingentity, 1.0);
					}
				}
			}
		}
	}
}
