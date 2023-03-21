package mod.azure.doom.entity.projectiles.entity;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.DoomProjectiles;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class BloodBoltEntity extends AbstractHurtingProjectile implements GeoEntity {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private float directHitDamage = 2;
	private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

	public BloodBoltEntity(EntityType<BloodBoltEntity> p_i50160_1_, Level p_i50160_2_) {
		super(p_i50160_1_, p_i50160_2_);
	}

	@Override
	public boolean displayFireAnimation() {
		return false;
	}

	public BloodBoltEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ, float directHitDamage) {
		super(DoomProjectiles.BLOODBOLT_MOB, shooter, accelX, accelY, accelZ, worldIn);
		this.directHitDamage = directHitDamage;
	}

	public BloodBoltEntity(Level worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(DoomProjectiles.BLOODBOLT_MOB, x, y, z, accelX, accelY, accelZ, worldIn);
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, event -> PlayState.CONTINUE));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return cache;
	}

	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
		super.shoot(x, y, z, velocity, inaccuracy);
		ticksInAir = 0;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putShort("life", (short) ticksInAir);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		ticksInAir = compound.getShort("life");
	}

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	public boolean isNoGravity() {
		if (isInWater())
			return false;
		return true;
	}

	public void setDirectHitDamage(float directHitDamage) {
		this.directHitDamage = directHitDamage;
	}

	@Override
	protected ParticleOptions getTrailParticle() {
		return ParticleTypes.PORTAL;
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (!level.isClientSide()) {
			explode();
			remove(Entity.RemovalReason.DISCARDED);
		}
		this.playSound(DoomSounds.UNMAKYR_FIRE, 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		if (!level.isClientSide()) {
			final var entity = entityHitResult.getEntity();
			final var entity2 = getOwner();
			entity.setSecondsOnFire(5);
			if (!(entity2 instanceof DemonEntity))
				entity.hurt(DamageSource.mobAttack((LivingEntity) entity2), directHitDamage);
			if (entity2 instanceof LivingEntity) {
				if (!(entity2 instanceof DemonEntity))
					doEnchantDamageEffects((LivingEntity) entity2, entity);
				remove(Entity.RemovalReason.DISCARDED);
			}
		}
		this.playSound(DoomSounds.UNMAKYR_FIRE, 1.0F, 1.2F / (random.nextFloat() * 0.2F + 0.9F));
	}

	protected void explode() {
		level.getEntities(this, new AABB(blockPosition().above()).inflate(8)).forEach(e -> doDamage(this, e));
	}

	private void doDamage(Entity user, Entity target) {
		if (target instanceof LivingEntity) {
			target.invulnerableTime = 0;
			target.hurt(DamageSource.indirectMagic(this, target), directHitDamage);
		}
	}

	@Override
	public void tick() {
		super.tick();
		if (tickCount >= 80)
			remove(Entity.RemovalReason.DISCARDED);
	}

}