package mod.azure.doom.entity.attack;

import mod.azure.doom.entity.DemonEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class AbstractDoubleRangedAttack implements IRangedDoubleAttack {

	public DemonEntity parentEntity;
	public double xOffSetModifier = 2;
	public double entityHeightFraction = 0.5;
	public double zOffSetModifier = 2;
	public float damage = 1;
	public double accuracy = 0.95;

	public AbstractDoubleRangedAttack(DemonEntity parentEntity) {
		this.parentEntity = parentEntity;
	}

	public AbstractDoubleRangedAttack(DemonEntity parentEntity, double xOffSetModifier, double entityHeightFraction,
			double zOffSetModifier, float damage) {
		this.parentEntity = parentEntity;
		this.xOffSetModifier = xOffSetModifier;
		this.entityHeightFraction = entityHeightFraction;
		this.zOffSetModifier = zOffSetModifier;
		this.damage = damage;
	}

	public AbstractDoubleRangedAttack setProjectileOriginOffset(double x, double entityHeightFraction, double z) {
		xOffSetModifier = x;
		this.entityHeightFraction = entityHeightFraction;
		zOffSetModifier = z;
		return this;
	}

	public AbstractDoubleRangedAttack setDamage(float damage) {
		this.damage = damage;
		return this;
	}

	private AttackSound sound;

	public AbstractDoubleRangedAttack setSound(AttackSound sound) {
		this.sound = sound;
		return this;
	}

	public AbstractDoubleRangedAttack setSound(SoundEvent sound, float volume, float pitch) {
		this.sound = new AttackSound(sound, volume, pitch);
		return this;
	}

	public AbstractDoubleRangedAttack setAccuracy(double accuracy) {
		this.accuracy = accuracy;
		return this;
	}

	public double rollAccuracy(double directional) {
		return directional + (1.0D - accuracy) * directional * this.parentEntity.getRandom().nextGaussian();
	}

	public void shoot() {
		LivingEntity livingentity = this.parentEntity.getTarget();
		World world = this.parentEntity.getWorld();
		Vec3d vector3d = this.parentEntity.getRotationVec(1.0F);
		double d2 = livingentity.getX() - (this.parentEntity.getX() + vector3d.x * xOffSetModifier);
		double d3 = livingentity.getBodyY(0.5D) - (this.parentEntity.getBodyY(entityHeightFraction));
		double d4 = livingentity.getZ() - (this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		ProjectileEntity projectile = getProjectile(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile.updatePosition(this.parentEntity.getX() + vector3d.x * xOffSetModifier,
				this.parentEntity.getBodyY(entityHeightFraction),
				this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		world.spawnEntity(projectile);
		if (sound == null)
			getDefaultAttackSound().play(this.parentEntity);
		else
			sound.play(this.parentEntity);
	}

	public void shoot2() {
		LivingEntity livingentity = this.parentEntity.getTarget();
		World world = this.parentEntity.getWorld();
		Vec3d vector3d = this.parentEntity.getRotationVec(1.0F);
		double d2 = livingentity.getX() - (this.parentEntity.getX() + vector3d.x * xOffSetModifier);
		double d3 = livingentity.getBodyY(0.5D) - (this.parentEntity.getBodyY(entityHeightFraction));
		double d4 = livingentity.getZ() - (this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		ProjectileEntity projectile = getProjectile2(world, rollAccuracy(d2), rollAccuracy(d3), rollAccuracy(d4));
		projectile.updatePosition(this.parentEntity.getX() + vector3d.x * xOffSetModifier,
				this.parentEntity.getBodyY(entityHeightFraction),
				this.parentEntity.getZ() + vector3d.z * zOffSetModifier);
		world.spawnEntity(projectile);
		if (sound == null)
			getDefaultAttackSound().play(this.parentEntity);
		else
			sound.play(this.parentEntity);
	}

}
