package mod.azure.doom.blocks.blockentities;

import mod.azure.doom.platform.Services;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BarrelEntity extends Entity {

    public BarrelEntity(EntityType<? extends BarrelEntity> entityType, Level world) {
        super(entityType, world);
        blocksBuilding = true;
    }

    public BarrelEntity(Level worldIn, double x, double y, double z) {
        this(Services.ENTITIES_HELPER.getBarrelEntity(), worldIn);
        this.absMoveTo(x, y, z);
        final var d = level().random.nextDouble() * 6.2831854820251465D;
        this.setDeltaMovement(-Math.sin(d) * 0.02D, 0.20000000298023224D, -Math.cos(d) * 0.02D);
        xo = x;
        yo = y;
        zo = z;
    }

    protected void explode() {
        level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 4.0F, true,
                Level.ExplosionInteraction.NONE);
    }

    @Override
    public void tick() {
        remove(RemovalReason.DISCARDED);
        if (!level().isClientSide()) explode();
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double distance) {
        return true;
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag nbt) {
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag nbt) {
    }

    @Override
    protected void defineSynchedData() {
    }

}