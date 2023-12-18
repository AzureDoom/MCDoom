package mod.azure.doom.entities;

import mod.azure.azurelib.ai.pathing.AzureNavigation;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.network.packet.EntityPacket;
import mod.azure.doom.entities.projectiles.entity.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public abstract class DemonEntity extends Monster implements NeutralMob, Enemy, GeoEntity {
    protected int flameTimer;
    private UUID targetUuid;
    protected int targetChangeTime;
    private static final UniformInt ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39);
    public static final EntityDataAccessor<Boolean> SCREAM = SynchedEntityData.defineId(DemonEntity.class,
            EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> STATE = SynchedEntityData.defineId(DemonEntity.class,
            EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ANGER_TIME = SynchedEntityData.defineId(DemonEntity.class,
            EntityDataSerializers.INT);

    protected DemonEntity(EntityType<? extends Monster> type, Level worldIn) {
        super(type, worldIn);
        xpReward = (int) getMaxHealth();
        setMaxUpStep(1.5f);
    }

    public static boolean canSpawnInDark(EntityType<? extends DemonEntity> ignoredType, LevelAccessor serverWorldAccess, MobSpawnType ignoredSpawnReason, BlockPos pos, RandomSource ignoredRandom) {
        if (serverWorldAccess.getDifficulty() == Difficulty.PEACEFUL) return false;
        return !serverWorldAccess.getBlockState(pos.below()).is(Blocks.NETHER_WART_BLOCK);
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    @Override
    public boolean canStandOnFluid(FluidState fluidState) {
        return fluidState.is(FluidTags.LAVA);
    }

    public int getAttckingState() {
        return entityData.get(STATE);
    }

    public void setAttackingState(int time) {
        entityData.set(STATE, time);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ANGER_TIME, 0);
        entityData.define(STATE, 0);
        entityData.define(SCREAM, false);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setAttackingState(compound.getInt("state"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("state", getAttckingState());
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return entityData.get(ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int ticks) {
        entityData.set(ANGER_TIME, ticks);
    }

    @Override
    public UUID getPersistentAngerTarget() {
        return targetUuid;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {
        targetUuid = uuid;
    }

    @Override
    public void startPersistentAngerTimer() {
        setRemainingPersistentAngerTime(ANGER_TIME_RANGE.sample(random));
    }

    @Override
    protected void tickDeath() {
        ++deathTime;
        if (deathTime == 80) {
            remove(RemovalReason.KILLED);
        }
    }

    @Override
    public void die(@NotNull DamageSource damageSource) {
        super.die(damageSource);
        this.triggerAnim("livingController", "death");
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level world) {
        return new AzureNavigation(this, world);
    }

    @Override
    public void playAmbientSound() {
        var soundEvent = getAmbientSound();
        if (soundEvent != null) this.playSound(soundEvent, 0.25F, getVoicePitch());
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (source == damageSources().inWall() || source == damageSources().onFire() || source == damageSources().inFire())
            return false;
        return super.hurt(source, amount);
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return EntityPacket.createPacket(this);
    }

    public void shootBloodBolt(float damage) {
        if (!this.level().isClientSide && this.getTarget() != null) {
            var projectile = new BloodBoltEntity(level(), this,
                    this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2),
                    this.getTarget().getY(0.5) - (this.getY(0.5)),
                    this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2), damage);
            projectile.setPos(this.getX() + this.getViewVector(1.0F).x, this.getY(0.5),
                    this.getZ() + this.getViewVector(1.0F).z);
            this.level().addFreshEntity(projectile);
        }
    }

    public void shootBolt(float damage) {
        if (!this.level().isClientSide && this.getTarget() != null) {
            var projectile = new DroneBoltEntity(level(), this,
                    this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2),
                    this.getTarget().getY(0.5) - (this.getY(0.5)),
                    this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2), damage);
            projectile.setPos(this.getX() + this.getViewVector(1.0F).x, this.getY(0.5),
                    this.getZ() + this.getViewVector(1.0F).z);
            this.level().addFreshEntity(projectile);
        }
    }

    public void shootEnergyCell(float damage) {
        if (!this.level().isClientSide && this.getTarget() != null) {
            var projectile = new EnergyCellMobEntity(level(), this,
                    this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2),
                    this.getTarget().getY(0.5) - (this.getY(0.5)),
                    this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2), damage);
            projectile.setPos(this.getX() + this.getViewVector(1.0F).x, this.getY(0.5),
                    this.getZ() + this.getViewVector(1.0F).z);
            this.level().addFreshEntity(projectile);
        }
    }

    public void shootMancubus(float damage) {
        if (!this.level().isClientSide && this.getTarget() != null) {
            var projectile = new FireProjectile(level(), this,
                    this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2),
                    this.getTarget().getY(0.5) - (this.getY(0.5)),
                    this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2), damage);
            projectile.setPos(this.getX() + this.getViewVector(1.0F).x, this.getY(0.5),
                    this.getZ() + this.getViewVector(1.0F).z);
            this.level().addFreshEntity(projectile);
        }
    }

    public void shootRocket(float damage) {
        if (!this.level().isClientSide && this.getTarget() != null) {
            var projectile = new RocketMobEntity(level(), this,
                    this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2),
                    this.getTarget().getY(0.5) - (this.getY(0.5)),
                    this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2), damage);
            projectile.setPos(this.getX() + this.getViewVector(1.0F).x, this.getY(0.5),
                    this.getZ() + this.getViewVector(1.0F).z);
            this.level().addFreshEntity(projectile);
        }
    }

    public void shootFireball(float damage, int offset) {
        if (!this.level().isClientSide && this.getTarget() != null) {
            var projectile = new CustomFireballEntity(level(), this,
                    this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2),
                    this.getTarget().getY(0.5) - (this.getY(0.5)),
                    this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2), damage);
            projectile.setPos((this.getX() + this.getViewVector(1.0F).x) + offset, this.getY(0.5),
                    this.getZ() + this.getViewVector(1.0F).z);
            this.level().addFreshEntity(projectile);
        }
    }

    public void shootBaron(float damage, double offsetx, double offsety, double offsetz) {
        if (!this.level().isClientSide && this.getTarget() != null) {
            var projectile = new BarenBlastEntity(level(), this,
                    this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2) + offsetx,
                    this.getTarget().getY(0.5) - (this.getY(0.5)) + offsety,
                    this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2) + offsetz, damage);
            projectile.setPos(this.getX() + this.getViewVector(1.0F).x + offsetx, this.getY(0.5) + offsety,
                    this.getZ() + this.getViewVector(1.0F).z + offsetz);
            this.level().addFreshEntity(projectile);
        }
    }

    public void shootSmallFireball(float damage) {
        if (!this.level().isClientSide && this.getTarget() != null) {
            var projectile = new CustomSmallFireballEntity(level(), this,
                    this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2),
                    this.getTarget().getY(0.5) - (this.getY(0.5)),
                    this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2), damage);
            projectile.setPos(this.getX() + this.getViewVector(1.0F).x, this.getY(0.5),
                    this.getZ() + this.getViewVector(1.0F).z);
            this.level().addFreshEntity(projectile);
        }
    }

    public void shootMace() {
        if (!this.level().isClientSide && this.getTarget() != null) {
            var projectile = new GladiatorMaceEntity(level(), this,
                    this.getTarget().getX() - (this.getX() + this.getViewVector(1.0F).x * 2),
                    this.getTarget().getY(0.5) - (this.getY(0.5)),
                    this.getTarget().getZ() - (this.getZ() + this.getViewVector(1.0F).z * 2));
            projectile.setPos(this.getX() + this.getViewVector(1.0F).x, this.getY(0.5),
                    this.getZ() + this.getViewVector(1.0F).z);
            this.level().addFreshEntity(projectile);
        }
    }

    public void throwPotion(LivingEntity target) {
        if (!this.level().isClientSide && this.getTarget() != null) {
            final var d0 = target.getX() + target.getDeltaMovement().x - this.getX();
            final var d1 = target.getEyeY() - 1.1F - this.getY();
            final var d2 = target.getZ() + target.getDeltaMovement().z - this.getZ();
            final var thrownpotion = new ThrownPotion(level(), this);
            thrownpotion.setItem(PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.POISON));
            thrownpotion.setXRot(thrownpotion.getXRot() + 20.0F);
            thrownpotion.shoot(d0, d1 + Math.sqrt(d0 * d0 + d2 * d2) * 0.2D, d2, 0.75F, 8.0F);
            thrownpotion.setPos(this.getX() + this.getViewVector(1.0F).x * 2, this.getY(0.5),
                    this.getZ() + this.getViewVector(1.0F).z * 2);
            level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WITCH_THROW, getSoundSource(),
                    1.0F, 0.8F + random.nextFloat() * 0.4F);
            this.level().addFreshEntity(thrownpotion);
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 10, false, false));
        }
    }

}