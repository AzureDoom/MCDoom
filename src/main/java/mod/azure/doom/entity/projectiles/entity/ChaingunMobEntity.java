package mod.azure.doom.entity.projectiles.entity;

import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.DoomSounds;
import mod.azure.doom.util.registry.DoomProjectiles;
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
import net.minecraft.world.phys.EntityHitResult;

public class ChaingunMobEntity extends AbstractHurtingProjectile {

	public int explosionPower = 1;
	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private float directHitDamage = 3F;

	public ChaingunMobEntity(EntityType<? extends ChaingunMobEntity> p_i50160_1_, Level p_i50160_2_) {
		super(p_i50160_1_, p_i50160_2_);
	}

	public ChaingunMobEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ,
			float directHitDamage) {
		super(DoomProjectiles.CHAINGUN_MOB, shooter, accelX, accelY, accelZ, worldIn);
		this.directHitDamage = directHitDamage;
	}

	public ChaingunMobEntity(Level worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(DoomProjectiles.CHAINGUN_MOB, x, y, z, accelX, accelY, accelZ, worldIn);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.tickCount >= 80)
			this.remove(Entity.RemovalReason.DISCARDED);
		if (this.level.isClientSide()) {
			double x = this.getX() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D;
			double z = this.getZ() + (this.random.nextDouble()) * (double) this.getBbWidth() * 0.5D;
			this.level.addParticle(ParticleTypes.SMOKE, true, x, this.getY(), z, 0, 0, 0);
		}
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

	@Override
	protected boolean shouldBurn() {
		return false;
	}

	public void setDirectHitDamage(float directHitDamage) {
		this.directHitDamage = directHitDamage;
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return EntityPacket.createPacket(this);
	}

	@Override
	public boolean isNoGravity() {
		if (this.isInWater())
			return false;
		return true;
	}

	@Override
	protected void onHitEntity(EntityHitResult entityHitResult) {
		super.onHitEntity(entityHitResult);
		if (!this.level.isClientSide()) {
			Entity entity = entityHitResult.getEntity();
			Entity entity2 = this.getOwner();
			if (!(entity2 instanceof DemonEntity))
				entity.hurt(DamageSource.mobAttack((LivingEntity) entity2), directHitDamage);
			if (entity2 instanceof LivingEntity) {
				if (!(entity2 instanceof DemonEntity))
					this.doEnchantDamageEffects((LivingEntity) entity2, entity);
				this.remove(Entity.RemovalReason.DISCARDED);
			}
		}
		this.playSound(DoomSounds.CHAINGUN_SHOOT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
	}
}