package mod.azure.doom.entities.projectiles;

import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.entities.tierboss.IconofsinEntity;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
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

public class EnergyCellEntity extends AbstractArrow implements GeoEntity {
    private float projectiledamage;
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    public SoundEvent hitSound = getDefaultHitGroundSoundEvent();

    public EnergyCellEntity(EntityType<? extends EnergyCellEntity> entityType, Level world) {
        super(entityType, world);
        pickup = Pickup.DISALLOWED;
    }

    public EnergyCellEntity(Level world, LivingEntity owner) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getEngeryCellEntity(), owner, world);
    }

    public EnergyCellEntity(Level world, LivingEntity owner, float damage) {
        super(mod.azure.doom.platform.Services.ENTITIES_HELPER.getEngeryCellEntity(), owner, world);
        projectiledamage = damage;
    }

    protected EnergyCellEntity(EntityType<? extends EnergyCellEntity> type, double x, double y, double z, Level world) {
        this(type, world);
    }

    protected EnergyCellEntity(EntityType<? extends EnergyCellEntity> type, LivingEntity owner, Level world) {
        this(type, owner.getX(), owner.getEyeY() - 0.10000000149011612D, owner.getZ(), world);
        setOwner(owner);
        if (owner instanceof Player) pickup = Pickup.DISALLOWED;
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
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, event -> PlayState.CONTINUE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
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
    public void tick() {
        super.tick();
        if (this.tickCount >= 80) remove(RemovalReason.DISCARDED);
        if (level().isClientSide())
            level().addParticle(mod.azure.doom.platform.Services.PARTICLES_HELPER.getPLASMA(), true, this.getX() + (random.nextDouble() * 2.0D - 1.0D) * getBbWidth() * 0.5D, this.getY(), this.getZ() + (random.nextDouble() * 2.0D - 1.0D) * getBbWidth() * 0.5D, 0, 0, 0);
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
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getPLASMA_HIT();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        if (!level().isClientSide()) remove(RemovalReason.DISCARDED);
        setSoundEvent(mod.azure.doom.platform.Services.SOUNDS_HELPER.getPLASMA_HIT());
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        final var entity = entityHitResult.getEntity();
        if (entityHitResult.getType() != HitResult.Type.ENTITY || !entityHitResult.getEntity().is(entity) && !level().isClientSide)
            remove(RemovalReason.KILLED);
        final var entity1 = getOwner();
        DamageSource damagesource;
        if (entity1 == null) damagesource = damageSources().arrow(this, this);
        else {
            damagesource = damageSources().arrow(this, entity1);
            if (entity1 instanceof LivingEntity livingEntity) livingEntity.setLastHurtMob(entity);
        }
        if (entity.hurt(damagesource, projectiledamage)) {
            if (entity instanceof LivingEntity livingentity) {
                if (!level().isClientSide && entity1 instanceof LivingEntity livingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                    EnchantmentHelper.doPostDamageEffects(livingEntity, livingentity);
                    if (this.isOnFire())
                        livingEntity.setSecondsOnFire(50);
                    level().explode(this, this.getX(), this.getY(0.0625D), this.getZ(), 1.5F, Level.ExplosionInteraction.NONE);
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