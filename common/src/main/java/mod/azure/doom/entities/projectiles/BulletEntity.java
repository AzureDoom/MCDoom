package mod.azure.doom.entities.projectiles;

import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierboss.IconofsinEntity;
import mod.azure.doom.entities.tierfodder.PossessedSoldierEntity;
import mod.azure.doom.helper.CommonUtils;
import mod.azure.doom.platform.Services;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.NotNull;

public class BulletEntity extends AbstractArrow {
    private int idleTicks = 0;
    private int plasmahits = 0;
    private int attachTimer = 0;
    private float projectiledamage;

    public static final EntityDataAccessor<Integer> PARTICLE = SynchedEntityData.defineId(BulletEntity.class,
            EntityDataSerializers.INT);

    public BulletEntity(EntityType<? extends BulletEntity> entityType, Level world) {
        super(entityType, world);
        this.pickup = Pickup.DISALLOWED;
    }

    public BulletEntity(Level world, LivingEntity owner, float damage) {
        super(Services.ENTITIES_HELPER.getBulletEntity(), owner, world);
        this.pickup = Pickup.DISALLOWED;
        this.setOwner(owner);
        this.projectiledamage = damage;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PARTICLE, 0);
    }

    public Integer useParticle() {
        return this.entityData.get(PARTICLE);
    }

    public void setParticle(Integer spin) {
        this.entityData.set(PARTICLE, spin);
    }

    @Override
    protected void doPostHurtEffects(@NotNull LivingEntity living) {
        super.doPostHurtEffects(living);
        if (!(living instanceof Player) && !(living instanceof IconofsinEntity)) {
            living.setDeltaMovement(0, 0, 0);
            living.invulnerableTime = 0;
        }
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    protected void tickDespawn() {
        if (tickCount >= 40) remove(RemovalReason.KILLED);
    }

    @Override
    protected boolean tryPickup(Player player) {
        return false;
    }

    @Override
    public void tick() {
        if (this.useParticle() >= 3) {
            final var idleOpt = 100;
            if (getDeltaMovement().lengthSqr() < 0.01) idleTicks++;
            else idleTicks = 0;
            if (idleOpt <= 0 || idleTicks < idleOpt) super.tick();
        } else {
            super.tick();
        }
        if (this.useParticle() >= 3 && this.useParticle() != 7)
            CommonUtils.spawnLightSource(this, this.level().isWaterAt(this.blockPosition()));
        if (this.tickCount >= 80) this.remove(RemovalReason.DISCARDED);
        if (this.inGroundTime > 1 && this.useParticle() != 7) {
            this.kill();
        }
        this.pickup = Pickup.DISALLOWED;
        CommonUtils.setOnFire(this);
        if (this.level().isClientSide()) {
            if (this.useParticle() == 1) this.level().addParticle(Services.PARTICLES_HELPER.getPISTOL(), true,
                    this.getX() + (this.random.nextDouble()) * this.getBbWidth() * 0.5D, this.getY(),
                    this.getZ() + (this.random.nextDouble()) * this.getBbWidth() * 0.5D, 0, 0, 0);
            if (this.useParticle() == 2) this.level().addParticle(ParticleTypes.SMOKE, true,
                    this.getX() + (this.random.nextDouble()) * this.getBbWidth() * 0.5D, this.getY(),
                    this.getZ() + (this.random.nextDouble()) * this.getBbWidth() * 0.5D, 0, 0, 0);
            if (this.useParticle() == 3 || this.useParticle() == 7 || this.useParticle() == 8)
                this.level().addParticle(ParticleTypes.ANGRY_VILLAGER, true, this.getX(), this.getY(), this.getZ(), 0,
                        0, 0);
            if (this.useParticle() == 4)
                this.level().addParticle(ParticleTypes.FLASH, true, this.getX(), this.getY(), this.getZ(), 0, 0, 0);
            if (this.useParticle() == 5) level().addParticle(Services.PARTICLES_HELPER.getUNMAYKR(), true,
                    this.getX() + random.nextDouble() * getBbWidth() * 0.5D, this.getY(),
                    this.getZ() + random.nextDouble() * getBbWidth() * 0.5D, 0, 0, 0);
            if (this.useParticle() == 6) level().addParticle(Services.PARTICLES_HELPER.getPLASMA(), true,
                    this.getX() + (random.nextDouble() * 2.0D - 1.0D) * getBbWidth() * 0.5D, this.getY(),
                    this.getZ() + (random.nextDouble() * 2.0D - 1.0D) * getBbWidth() * 0.5D, 0, 0, 0);
        }
        if (!this.level().isClientSide()) this.attachTimer++;
        if (!this.isPassenger() && this.attachTimer >= 20 && this.useParticle() == 7) {
            this.explode(10f);
            this.remove(RemovalReason.DISCARDED);
        }
        if (this.isPassenger() && this.attachTimer >= 20 && this.useParticle() == 7) {
            this.explode(10f);
            this.remove(RemovalReason.DISCARDED);
        }
        if (this.attachTimer >= 20 && this.useParticle() != 7) this.remove(RemovalReason.DISCARDED);
        if (this.isPassenger()) this.setDeltaMovement(0, 0, 0);
        if (this.useParticle() == 8) {
            var livingEntities = level().getEntitiesOfClass(
                    Monster.class, new AABB(this.getX() - 6.0, this.getY() - 6.0, this.getZ() - 6.0, this.getX() + 6.0,
                            this.getY() + 6.0, this.getZ() + 6.0), entity1 -> entity1 != this.getOwner());
            if (!livingEntities.isEmpty()) {
                var first = livingEntities.get(0);
                var entityPos = new Vec3(first.getX(), first.getY() + first.getEyeHeight(), first.getZ());
                var newPath = entityPos.subtract(this.getX(), this.getY() + this.getEyeHeight(),
                        this.getZ()).normalize().add(
                        this.getDeltaMovement().normalize().multiply(4.0, 4.0, 4.0)).normalize();
                var speed = this.getDeltaMovement().length();
                this.setDeltaMovement(newPath.multiply(speed, speed, speed));
            }
        }
    }

    @Override
    public @NotNull ItemStack getPickupItem() {
        return new ItemStack(Items.AIR);
    }

    @Override
    public boolean isNoGravity() {
        return !this.isInWater();
    }

    @Override
    public void setSoundEvent(@NotNull SoundEvent soundIn) {
        this.getDefaultHitGroundSoundEvent();
    }

    @Override
    protected @NotNull SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.ARMOR_EQUIP_IRON;
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (this.useParticle() != 7 && !this.level().isClientSide()) this.remove(RemovalReason.DISCARDED);
        this.setSoundEvent(SoundEvents.ARMOR_EQUIP_IRON);
        if (this.useParticle() == 6) setSoundEvent(Services.SOUNDS_HELPER.getPLASMA_HIT());
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        var entity = entityHitResult.getEntity();
        if (entityHitResult.getType() != HitResult.Type.ENTITY || !(entityHitResult).getEntity().is(
                entity) && !this.level().isClientSide) this.remove(RemovalReason.KILLED);
        var entity1 = this.getOwner();
        if (this.useParticle() != 7) {
            if (entity.hurt(damageSources().thrown(entity, this.getOwner()), projectiledamage)) {
                if (entity instanceof LivingEntity livingEntity) {
                    if (!this.level().isClientSide && entity1 instanceof LivingEntity livingEntity1) {
                        if (this.useParticle() != 7) {
                            EnchantmentHelper.doPostHurtEffects(livingEntity, livingEntity1);
                            EnchantmentHelper.doPostDamageEffects(livingEntity1, livingEntity);
                        }
                        if (this.isOnFire()) livingEntity.setSecondsOnFire(50);
                        if (this.useParticle() == 3 || this.useParticle() == 4)
                            this.explode(MCDoom.config.argent_bolt_damage);
                        if (this.useParticle() == 6 && livingEntity instanceof PossessedSoldierEntity possessedSoldier && possessedSoldier.getVariant() == 3)
                            possessedSoldier.setPlasmaHits(1);
                        if (this.useParticle() != 7) this.remove(RemovalReason.KILLED);
                        if (this.useParticle() == 7) this.attachTimer++;
                        if (this.useParticle() == 8) this.explode(20f);
                    }
                    this.doPostHurtEffects(livingEntity);
                    if (livingEntity != entity1 && livingEntity instanceof Player && entity1 instanceof ServerPlayer serverPlayer && !this.isSilent())
                        serverPlayer.connection.send(
                                new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }
            }
        } else {
            this.startRiding(entity);
        }
    }

    protected void explode(float damage) {
        this.level().getEntitiesOfClass(LivingEntity.class,
                new AABB(this.blockPosition().above()).inflate(3D, 3D, 3D)).forEach(e -> {
            if (e != this.getOwner()) {
                if (this.isOnFire()) e.setSecondsOnFire(50);
                e.hurt(damageSources().playerAttack((Player) this.getOwner()), damage);
            }
        });
    }

    @Override
    public void remove(@NotNull RemovalReason reason) {
        if (this.useParticle() == 3 || this.useParticle() == 4 || this.useParticle() == 7) {
            var areaeffectcloudentity = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
            areaeffectcloudentity.setParticle(ParticleTypes.LAVA);
            areaeffectcloudentity.setRadius(6);
            areaeffectcloudentity.setDuration(1);
            areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
            this.level().addFreshEntity(areaeffectcloudentity);
            level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE,
                    SoundSource.PLAYERS, 1.0F, 1.5F);
            super.remove(reason);
        } else {
            super.remove(reason);
        }
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

}