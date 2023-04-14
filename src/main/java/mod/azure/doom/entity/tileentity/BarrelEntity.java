package mod.azure.doom.entity.tileentity;

import mod.azure.doom.util.registry.DoomProjectiles;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class BarrelEntity extends Entity {

	private LivingEntity causingEntity;

	public BarrelEntity(EntityType<? extends BarrelEntity> entityType, Level world) {
		super(entityType, world);
		this.blocksBuilding = true;
	}

	protected void explode() {
		this.level.explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, true, Level.ExplosionInteraction.NONE);
	}

	public BarrelEntity(Level worldIn, double x, double y, double z, LivingEntity igniter) {
		this(DoomProjectiles.BARREL.get(), worldIn);
		this.absMoveTo(x, y, z);
		double d = level.random.nextDouble() * 6.2831854820251465D;
		this.setDeltaMovement(-Math.sin(d) * 0.02D, 0.20000000298023224D, -Math.cos(d) * 0.02D);
		this.xo = x;
		this.yo = y;
		this.zo = z;
		this.causingEntity = igniter;
	}

	public LivingEntity getCausingEntity() {
		return this.causingEntity;
	}

	public void tick() {
		this.remove(Entity.RemovalReason.DISCARDED);
		if (!this.level.isClientSide()) {
			this.explode();
		}
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
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