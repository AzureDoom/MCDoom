package mod.azure.doom.entity.tileentity;

import mod.azure.doom.util.registry.DoomEntities;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class BarrelEntity extends Entity {

	private LivingEntity causingEntity;

	public BarrelEntity(EntityType<? extends BarrelEntity> entityType, Level world) {
		super(entityType, world);
		blocksBuilding = true;
	}

	protected void explode() {
		level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, true, Level.ExplosionInteraction.NONE);
	}

	public BarrelEntity(Level worldIn, double x, double y, double z, LivingEntity igniter) {
		this(DoomEntities.BARREL, worldIn);
		this.absMoveTo(x, y, z);
		final var d = level().random.nextDouble() * 6.2831854820251465D;
		this.setDeltaMovement(-Math.sin(d) * 0.02D, 0.20000000298023224D, -Math.cos(d) * 0.02D);
		xo = x;
		yo = y;
		zo = z;
		causingEntity = igniter;
	}

	public LivingEntity getCausingEntity() {
		return causingEntity;
	}

	@Override
	public void tick() {
		remove(Entity.RemovalReason.DISCARDED);
		if (!level().isClientSide())
			explode();
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return new ClientboundAddEntityPacket(this);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean shouldRenderAtSqrDistance(double distance) {
		return true;
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
	}

	@Override
	protected void defineSynchedData() {
	}

}