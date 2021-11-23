package mod.azure.doom.entity.ai.goal;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.projectiles.CustomSmallFireballEntity;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import mod.azure.doom.entity.tierheavy.Revenant2016Entity;
import mod.azure.doom.util.ModSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FlyingRangeAttackGoal extends Goal {
	private final DemonEntity actor;
	private float damage;
	private final double speed = 1.0D;
	private int cooldown = -1;
	private int targetSeeingTicker;
	private SoundEvent sound;
	private boolean rockets;

	public FlyingRangeAttackGoal(DemonEntity zombie, float damage, SoundEvent firingsound, boolean useRockets) {
		this.actor = zombie;
		this.damage = damage;
		this.sound = firingsound;
		this.rockets = useRockets;
	}

	public boolean canStart() {
		return this.actor.getTarget() == null ? false : true;
	}

	public boolean shouldContinue() {
		return (this.canStart() || !this.actor.getNavigation().isIdle());
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
		this.actor.setAttackingState(0);
	}

	public void tick() {
		LivingEntity livingEntity = this.actor.getTarget();
		if (livingEntity != null) {
			boolean bl = this.actor.getVisibilityCache().canSee(livingEntity);
			boolean bl2 = this.targetSeeingTicker > 0;
			++this.cooldown;
			if (bl != bl2) {
				this.targetSeeingTicker = 0;
			}

			if (bl) {
				++this.targetSeeingTicker;
			} else {
				--this.targetSeeingTicker;
			}
			this.actor.lookAtEntity(livingEntity, 30.0F, 30.0F);
			if (bl) {
				if (rockets == true) {
					if (this.cooldown == 15 && this.targetSeeingTicker >= -60) {
						this.shootRocket(livingEntity);
					}
					if (this.cooldown == 20 && this.targetSeeingTicker >= -60) {
						this.shootRocket(livingEntity);
					}
					if (this.cooldown == 45) {
						this.cooldown = -150;
						this.actor.setAttackingState(0);
						this.actor.getNavigation().startMovingTo(livingEntity, this.speed);
					}
				} else {
					if (this.cooldown == 15 && this.targetSeeingTicker >= -60) {
						this.shootFireball(livingEntity);
					}
					if (this.cooldown == 25) {
						this.cooldown = -150;
						this.actor.setAttackingState(0);
						this.actor.getNavigation().startMovingTo(livingEntity, this.speed);
					}
				}
			}
		}
	}

	public void shootFireball(LivingEntity livingentity) {
		World world = this.actor.world;
		Vec3d vec3d = this.actor.getRotationVec(1.0F);
		double f = livingentity.getX() - (this.actor.getX() + vec3d.x * 2.0D);
		double g = livingentity.getBodyY(0.5D) - (0.5D + this.actor.getBodyY(0.5D));
		double h = livingentity.getZ() - (this.actor.getZ() + vec3d.z * 2.0D);
		CustomSmallFireballEntity fireballEntity = new CustomSmallFireballEntity(world, this.actor, f, g, h, damage);
		fireballEntity.updatePosition(this.actor.getX() + vec3d.x * 2.0D, this.actor.getBodyY(0.5D) + 0.75D,
				actor.getZ() + vec3d.z * 2.0D);
		actor.world.playSoundFromEntity(null, actor, sound, SoundCategory.HOSTILE, 0.5F, 1.0F);
		world.spawnEntity(fireballEntity);
		this.actor.setAttackingState(1);
	}

	public void shootRocket(LivingEntity livingentity) {
		World world = this.actor.world;
		Vec3d vec3d = this.actor.getRotationVec(1.0F);
		double f = livingentity.getX() - (this.actor.getX() + vec3d.x * 2.0D);
		double g = livingentity.getBodyY(0.5D) - (0.5D + this.actor.getBodyY(0.5D));
		double h = livingentity.getZ() - (this.actor.getZ() + vec3d.z * 2.0D);
		RocketMobEntity fireballEntity = new RocketMobEntity(world, this.actor, f, g, h, damage);
		fireballEntity.updatePosition(this.actor.getX() + vec3d.x * 2.0D, this.actor.getBodyY(0.5D) + 0.75D,
				actor.getZ() + vec3d.z * 2.0D);
		actor.world.playSoundFromEntity(null, actor,
				(((Revenant2016Entity) actor).getVariant() == 10 ? ModSoundEvents.REVENANT_DOOT
						: ModSoundEvents.REVENANT_ATTACK),
				SoundCategory.HOSTILE, 0.5F, 1.0F);
		world.spawnEntity(fireballEntity);
	}
}