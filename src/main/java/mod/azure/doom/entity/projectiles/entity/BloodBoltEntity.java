package mod.azure.doom.entity.projectiles.entity;

import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.DoomEntities;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class BloodBoltEntity extends AbstractHurtingProjectile implements IAnimatable {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private float directHitDamage = 2;

	public BloodBoltEntity(EntityType<BloodBoltEntity> entity, Level world) {
		super(entity, world);
	}

	private AnimationFactory factory = new AnimationFactory(this);

	private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
		event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimationData data) {
		data.addAnimationController(new AnimationController<BloodBoltEntity>(this, "controller", 0, this::predicate));
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	public BloodBoltEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ,
			float directHitDamage) {
		super(DoomEntities.BLOODBOLT_MOB.get(), shooter, accelX, accelY, accelZ, worldIn);
		this.directHitDamage = directHitDamage;
	}

	public BloodBoltEntity(Level worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(DoomEntities.BLOODBOLT_MOB.get(), x, y, z, accelX, accelY, accelZ, worldIn);
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		super.shoot(x, y, z, velocity, inaccuracy);
		this.ticksInAir = 0;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putShort("life", (short) this.ticksInAir);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.ticksInAir = compound.getShort("life");
	}

	public void setDirectHitDamage(float directHitDamage) {
		this.directHitDamage = directHitDamage;
	}

	@Override
	protected ParticleOptions getTrailParticle() {
		return ParticleTypes.PORTAL;
	}

	@Override
	public void tick() {
		super.tick();
		this.level.addParticle(this.getTrailParticle(), this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	public boolean isNoGravity() {
		if (this.isInWater()) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean isPushedByFluid() {
		return false;
	}

	@Override
	public boolean isPickable() {
		return false;
	}

	@Override
	protected void onHitEntity(EntityHitResult p_213868_1_) {
		super.onHitEntity(p_213868_1_);
		if (!this.level.isClientSide) {
			Entity entity = p_213868_1_.getEntity();
			Entity entity1 = this.getOwner();
			if (!(entity instanceof DemonEntity))
				entity.hurt(DamageSource.mobAttack((LivingEntity) entity1), directHitDamage);
			this.remove(RemovalReason.KILLED);
			if (entity1 instanceof LivingEntity) {
				if (!(entity instanceof DemonEntity))
					this.doEnchantDamageEffects((LivingEntity) entity1, entity);
			}
		}
		this.playSound(DoomSounds.UNMAKYR_FIRE.get(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
	}

}