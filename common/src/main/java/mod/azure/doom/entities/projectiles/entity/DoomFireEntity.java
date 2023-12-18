package mod.azure.doom.entities.projectiles.entity;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.entities.DemonEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class DoomFireEntity extends Entity implements GeoEntity {

    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private int warmupDelayTicks;
    private boolean sentSpikeEvent;
    private int lifeTicks;
    private UUID casterUuid;
    private float damage;

    public DoomFireEntity(EntityType<DoomFireEntity> entityType, Level world) {
        super(entityType, world);
        lifeTicks = 75;
    }

    public DoomFireEntity(Level worldIn, double x, double y, double z, int warmup, LivingEntity casterIn, float damage) {
        this(mod.azure.doom.platform.Services.ENTITIES_HELPER.getDoomFireEntity(), worldIn);
        warmupDelayTicks = warmup;
        setCaster(casterIn);
        this.absMoveTo(x, y, z);
        this.damage = damage;
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
    protected void defineSynchedData() {
    }

    public void setCaster(@Nullable LivingEntity owner) {
        casterUuid = owner == null ? null : owner.getUUID();
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        warmupDelayTicks = compound.getInt("Warmup");
        if (compound.hasUUID("Owner"))
            casterUuid = compound.getUUID("Owner");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("Warmup", warmupDelayTicks);
        if (casterUuid != null)
            compound.putUUID("Owner", casterUuid);
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        explode();
        super.remove(reason);
    }

    protected void explode() {
        level().getEntities(this, new AABB(blockPosition().above()).inflate(8)).forEach(this::doDamage);
    }

    private void doDamage(Entity target) {
        if (target instanceof LivingEntity) {
            if (target instanceof DemonEntity)
                return;

            target.invulnerableTime = 0;
            target.hurt(damageSources().indirectMagic(this, target), damage);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (--warmupDelayTicks < 0) {
            if (!sentSpikeEvent) {
                level().broadcastEntityEvent(this, (byte) 4);
                sentSpikeEvent = true;
            }

            if (--lifeTicks < 0)
                remove(RemovalReason.KILLED);
        }
        if (isAlive() && level().getBlockState(blockPosition().above()).isAir())
            level().setBlockAndUpdate(blockPosition().above(),
                    BaseFireBlock.getState(level(), blockPosition().above()));
        level().getEntities(this, new AABB(blockPosition().above()).inflate(1)).forEach(e -> {
            if (e.isAlive() && !(e instanceof DemonEntity)) {
                e.hurt(damageSources().lava(), damage);
                e.setRemainingFireTicks(60);
            }
        });
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (source == damageSources().inWall() || source == damageSources().onFire() || source == damageSources().inFire())
            return false;
        return super.hurt(source, amount);
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

}