package mod.azure.doom.entity.projectiles.entity;

import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.util.registry.DoomSounds;
import mod.azure.doom.util.registry.ProjectilesEntityRegister;
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
import net.minecraft.world.phys.HitResult;

public class DroneBoltEntity extends AbstractHurtingProjectile {

	protected int timeInAir;
	protected boolean inAir;
	private int ticksInAir;
	private float directHitDamage = 2;

	public DroneBoltEntity(EntityType<DroneBoltEntity> p_i50160_1_, Level p_i50160_2_) {
		super(p_i50160_1_, p_i50160_2_);
	}

	public DroneBoltEntity(Level worldIn, LivingEntity shooter, double accelX, double accelY, double accelZ,
			float directHitDamage) {
		super(ProjectilesEntityRegister.DRONEBOLT_MOB, shooter, accelX, accelY, accelZ, worldIn);
		this.directHitDamage = directHitDamage;
	}

	public DroneBoltEntity(Level worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
		super(ProjectilesEntityRegister.DRONEBOLT_MOB, x, y, z, accelX, accelY, accelZ, worldIn);
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
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!this.level.isClientSide()) {
			this.explode();
			this.remove(Entity.RemovalReason.DISCARDED);
		}
		this.playSound(DoomSounds.ROCKET_HIT, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
	}

	protected void explode() {
		this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.0F, false,
				DoomConfig.enable_block_breaking ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE);
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
		this.playSound(DoomSounds.UNMAKYR_FIRE, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
	}

	@Override
	public void tick() {
		super.tick();
		if (this.tickCount >= 80)
			this.remove(Entity.RemovalReason.DISCARDED);
	}

}