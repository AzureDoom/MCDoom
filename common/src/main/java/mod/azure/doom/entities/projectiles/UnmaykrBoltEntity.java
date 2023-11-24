package mod.azure.doom.entities.projectiles;

import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.doom.entities.tierboss.IconofsinEntity;
import mod.azure.doom.helper.CommonUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class UnmaykrBoltEntity extends AbstractArrow {
    private float projectiledamage;
    private int idleTicks = 0;
    public SoundEvent hitSound = getDefaultHitGroundSoundEvent();

    public UnmaykrBoltEntity(EntityType<? extends UnmaykrBoltEntity> entityType, Level world) {
        super(entityType, world);
        pickup = Pickup.DISALLOWED;
    }

    public UnmaykrBoltEntity(Level world, LivingEntity owner) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getUnmakyrBoltEntity(), owner, world);
    }

    public UnmaykrBoltEntity(Level world, LivingEntity owner, float damage) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getUnmakyrBoltEntity(), owner, world);
        projectiledamage = damage;
    }

    protected UnmaykrBoltEntity(EntityType<? extends UnmaykrBoltEntity> type, double x, double y, double z, Level world) {
        this(type, world);
    }

    protected UnmaykrBoltEntity(EntityType<? extends UnmaykrBoltEntity> type, LivingEntity owner, Level world) {
        this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
        setOwner(owner);
        if (owner instanceof Player) pickup = Pickup.DISALLOWED;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return EntityPacket.createPacket(this);
    }

    @Override
    protected void tickDespawn() {
        if (tickCount >= 40) remove(RemovalReason.KILLED);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        super.doPostHurtEffects(living);
        if (!(living instanceof Player) && !(living instanceof IconofsinEntity)) {
            living.setDeltaMovement(0, 0, 0);
            living.invulnerableTime = 0;
        }
    }

    @Override
    public void tick() {
        final var idleOpt = 100;
        if (getDeltaMovement().lengthSqr() < 0.01) idleTicks++;
        else idleTicks = 0;
        if (idleOpt <= 0 || idleTicks < idleOpt) super.tick();
        if (this.tickCount >= 80) remove(RemovalReason.DISCARDED);
        final var isInsideWaterBlock = level().isWaterAt(blockPosition());
        CommonUtils.spawnLightSource(this, isInsideWaterBlock);
        if (level().isClientSide())
            level().addParticle(mod.azure.doom.platform.Services.PARTICLES_HELPER.getUNMAYKR(), true, this.getX() + random.nextDouble() * getBbWidth() * 0.5D, this.getY(), this.getZ() + random.nextDouble() * getBbWidth() * 0.5D, 0, 0, 0);
    }

    @Override
    public boolean isNoGravity() {
        return !isInWater();
    }

    @Override
    public void setSoundEvent(SoundEvent soundIn) {
        hitSound = soundIn;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.ARMOR_EQUIP_IRON;
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!level().isClientSide()) remove(RemovalReason.DISCARDED);
        setSoundEvent(SoundEvents.ARMOR_EQUIP_IRON);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        final var entity = entityHitResult.getEntity();
        if (entityHitResult.getType() != HitResult.Type.ENTITY || !entityHitResult.getEntity().is(entity) && !level().isClientSide)
            remove(RemovalReason.KILLED);
        final var entity1 = getOwner();
        DamageSource damagesource;
        if (entity1 == null) damagesource = damageSources().indirectMagic(this, this);
        else {
            damagesource = damageSources().indirectMagic(this, entity1);
            if (entity1 instanceof LivingEntity livingEntity) livingEntity.setLastHurtMob(entity);
        }
        if (entity.hurt(damagesource, projectiledamage)) {
            if (entity instanceof LivingEntity livingentity) {
                if (!level().isClientSide && entity1 instanceof LivingEntity livingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                    EnchantmentHelper.doPostDamageEffects(livingEntity, livingentity);
                    if (this.isOnFire())
                        livingEntity.setSecondsOnFire(50);
                    remove(RemovalReason.KILLED);
                }
                doPostHurtEffects(livingentity);
                if (entity1 != null && livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !isSilent())
                    ((ServerPlayer) entity1).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
            }
        } else if (!level().isClientSide) remove(RemovalReason.KILLED);
    }

    @Override
    public ItemStack getPickupItem() {
        return Items.AIR.getDefaultInstance();
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }
}