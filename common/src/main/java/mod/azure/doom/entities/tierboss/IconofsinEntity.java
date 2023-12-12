package mod.azure.doom.entities.tierboss;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.entities.DoomAnimationsDefault;
import mod.azure.doom.entities.projectiles.entity.DoomFireEntity;
import mod.azure.doom.entities.task.DemonMeleeAttack;
import mod.azure.doom.entities.task.DemonProjectileAttack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetRandomWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.path.SetWalkTargetToAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.InvalidateAttackTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetPlayerLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.SetRandomLookTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.target.TargetOrRetaliate;
import net.tslat.smartbrainlib.api.core.sensor.ExtendedSensor;
import net.tslat.smartbrainlib.api.core.sensor.custom.UnreachableTargetSensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.HurtBySensor;
import net.tslat.smartbrainlib.api.core.sensor.vanilla.NearbyLivingEntitySensor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class IconofsinEntity extends DemonEntity implements SmartBrainOwner<IconofsinEntity> {

    public static final EntityDataAccessor<Integer> DEATH_STATE = SynchedEntityData.defineId(IconofsinEntity.class,
            EntityDataSerializers.INT);
    private final ServerBossEvent bossInfo = (ServerBossEvent) new ServerBossEvent(getDisplayName(),
            BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true).setCreateWorldFog(
            true);
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private int summonTime = -1;

    public IconofsinEntity(EntityType<IconofsinEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    public static AttributeSupplier.@NotNull Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH,
                MCDoom.config.icon_health).add(Attributes.ATTACK_DAMAGE, MCDoom.config.icon_melee_damage).add(
                Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D).add(
                Attributes.KNOCKBACK_RESISTANCE, 1000.0D);
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        var isDead = this.dead || this.getHealth() < 0.01 || this.isDeadOrDying();
        controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
            if (event.isMoving() && event.getAnimatable().getDeathState() == 0)
                return event.setAndContinue(DoomAnimationsDefault.WALKING);
            if (isDead && event.getAnimatable().getDeathState() == 0)
                return event.setAndContinue(DoomAnimationsDefault.DEATH_PHASEONE);
            if (isDead && event.getAnimatable().getDeathState() == 1)
                return event.setAndContinue(DoomAnimationsDefault.DEATH);
            if (event.isMoving() && event.getAnimatable().getDeathState() == 1)
                return event.setAndContinue(DoomAnimationsDefault.WALKING_NOHELMET);
            if ((event.getAnimatable().getDeathState() == 1) || (!event.isMoving() && hurtMarked && event.getAnimatable().getDeathState() == 1))
                return event.setAndContinue(DoomAnimationsDefault.IDLE_NOHELMET);
            return event.setAndContinue(DoomAnimationsDefault.IDLE);
        }).setSoundKeyframeHandler(event -> {
            if (event.getKeyframeData().getSound().matches("walk") && (level().isClientSide()))
                level().playLocalSound(this.getX(), this.getY(), this.getZ(),
                        mod.azure.doom.platform.Services.SOUNDS_HELPER.getCYBERDEMON_STEP(), SoundSource.HOSTILE, 0.25F,
                        1.0F, false);
        })).add(new AnimationController<>(this, "attackController", 0, event -> PlayState.STOP).triggerableAnim(
                "phaseoneranged", DoomAnimationsDefault.SUMMONED).triggerableAnim("phasetworanged",
                DoomAnimationsDefault.SUMMONED_NOHELMET).triggerableAnim("phaseonestomp",
                DoomAnimationsDefault.STOMP).triggerableAnim("phasetwostomp", DoomAnimationsDefault.STOMP_NOHELMET));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void tickDeath() {
        ++deathTime;
        if (deathTime == 80 && this.getDeathState() == 0) {
            setHealth(getMaxHealth());
            setDeathState(1);
            deathTime = 0;
        }
        if (deathTime == 40 && this.getDeathState() == 1) {
            remove(Entity.RemovalReason.KILLED);
            dropExperience();
        }
    }

    public int getDeathState() {
        return entityData.get(DEATH_STATE);
    }

    public void setDeathState(int state) {
        entityData.set(DEATH_STATE, state);
    }

    @Override
    public void die(@NotNull DamageSource source) {
        if (!level().isClientSide) {
            if (source == damageSources().fellOutOfWorld()) setDeathState(1);
            if (this.getDeathState() == 0) {
                final var areaeffectcloudentity = new AreaEffectCloud(level(), this.getX(), this.getY(), this.getZ());
                areaeffectcloudentity.setParticle(ParticleTypes.EXPLOSION);
                areaeffectcloudentity.setRadius(3.0F);
                areaeffectcloudentity.setDuration(55);
                areaeffectcloudentity.setPos(this.getX(), this.getY(), this.getZ());
                level().addFreshEntity(areaeffectcloudentity);
                goalSelector.getRunningGoals().forEach(WrappedGoal::stop);
                setLastHurtMob(Objects.requireNonNull(getLastHurtByMob()));
                level().broadcastEntityEvent(this, (byte) 3);
            }
            if (this.getDeathState() == 1) super.die(source);
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, @NotNull DamageSource damageSource) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushEntities() {
    }

    @Override
    protected boolean canRide(@NotNull Entity entity) {
        return false;
    }

    @Override
    public void knockback(double x, double y, double z) {
        super.knockback(0, 0, 0);
    }

    @Override
    protected Brain.@NotNull Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<ExtendedSensor<IconofsinEntity>> getSensors() {
        return ObjectArrayList.of(new NearbyLivingEntitySensor<IconofsinEntity>().setRadius(32).setPredicate(
                        (target, entity) -> target.isAlive() && !(target instanceof DemonEntity)), new HurtBySensor<>(),
                new UnreachableTargetSensor<>());
    }

    @Override
    public BrainActivityGroup<IconofsinEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300),
                new FloatToSurfaceOfFluid<>(), new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<IconofsinEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<IconofsinEntity>(
                        new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()),
                        new SetPlayerLookTarget<>().stopIf(
                                target -> !target.isAlive() || target instanceof Player player && player.isCreative()),
                        new SetRandomLookTarget<>()),
                new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(1.0f),
                        new Idle<>().runFor(entity -> entity.getRandom().nextInt(300, 600))));
    }

    @Override
    public BrainActivityGroup<IconofsinEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(
                new InvalidateAttackTarget<>().invalidateIf((target, entity) -> !target.isAlive()),
                new SetWalkTargetToAttackTarget<>().speedMod((owner, target) -> 1.05F),
                new DemonProjectileAttack<>(5).attackInterval(mob -> 240), new DemonMeleeAttack<>(5));
    }

    public void spawnWave(int WaveAmount, LivingEntity entity) {
        final var rand = getRandom();
        final var waveEntries = Arrays.asList(MCDoom.config.icon_wave_entries);
        for (var k = 1; k < WaveAmount; ++k) {
            final var r = this.getRandom().nextInt(-3, 3);
            for (var i = 0; i < 1; ++i) {
                final var randomIndex = rand.nextInt(waveEntries.size());
                final var randomElement1 = new ResourceLocation(waveEntries.get(randomIndex));
                final var randomElement = BuiltInRegistries.ENTITY_TYPE.get(randomElement1);
                final var waveentity = randomElement.create(level());
                assert waveentity != null;
                waveentity.setPos(entity.getX() + r, entity.getY() + 0.5D, entity.getZ() + r);
                level().addFreshEntity(waveentity);
            }
        }
    }

    public void spawnFlames(double x, double z, double maxY, double y, float yaw) {
        var blockpos = BlockPos.containing(x, y, z);
        var flag = false;
        var d0 = 0.0D;
        do {
            final var blockpos1 = blockpos.below();
            final var blockstate = level().getBlockState(blockpos1);
            if (blockstate.isFaceSturdy(level(), blockpos1, Direction.UP)) {
                if (!level().isEmptyBlock(blockpos)) {
                    final var blockstate1 = level().getBlockState(blockpos);
                    final var voxelshape = blockstate1.getCollisionShape(level(), blockpos);
                    if (!voxelshape.isEmpty()) d0 = voxelshape.max(Direction.Axis.Y);
                }
                flag = true;
                break;
            }
            blockpos = blockpos.below();
        } while (blockpos.getY() >= Mth.floor(maxY) - 1);

        if (flag) {
            final var fang = new DoomFireEntity(level(), x, blockpos.getY() + d0, z, yaw, 1, this,
                    MCDoom.config.icon_melee_damage + (this.getDeathState() == 1 ? MCDoom.config.icon_phaseone_damage_boos : 0));
            fang.setSecondsOnFire(tickCount);
            fang.setInvisible(false);
            level().addFreshEntity(fang);
        }
    }

    @Override
    protected float getStandingEyeHeight(@NotNull Pose poseIn, @NotNull EntityDimensions sizeIn) {
        return 18.70F;
    }

    protected boolean shouldDrown() {
        return false;
    }

    protected boolean shouldBurnInDay() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getICON_AMBIENT();
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource damageSourceIn) {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getICON_HURT();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getICON_DEATH();
    }

    public ServerBossEvent getBossInfo() {
        return bossInfo;
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer player) {
        super.startSeenByPlayer(player);
        bossInfo.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer player) {
        super.stopSeenByPlayer(player);
        bossInfo.removePlayer(player);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(DEATH_STATE, 0);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (hasCustomName()) bossInfo.setName(getDisplayName());
        setDeathState(compound.getInt("Phase"));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Phase", getDeathState());
    }

    @Override
    public void setCustomName(Component name) {
        super.setCustomName(name);
        bossInfo.setName(getDisplayName());
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
        super.customServerAiStep();
        bossInfo.setProgress(getHealth() / getMaxHealth());
    }

    @Override
    public int getArmorValue() {
        return this.getDeathState() == 1 ? 0 : (int) (getHealth() / getMaxHealth() * 100);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        ++tickCount;
        if (!level().isClientSide) {
            if (this.getDeathState() == 0) this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000000, 1));
            else if (this.getDeathState() == 1) {
                removeEffect(MobEffects.DAMAGE_BOOST);
                this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10000000, 2));
                this.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10000000, 1));
            }
            if (!level().dimensionType().respawnAnchorWorks()) {
                this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 10000000, 3));
                if (tickCount % 2400 == 0) heal(40F);
            }
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
        final var aabb = new AABB(blockPosition().above()).inflate(64D, 64D, 64D);
        level().getEntities(this, aabb).forEach(e -> {
            if (e instanceof IconofsinEntity && e.tickCount < 1) e.remove(RemovalReason.KILLED);
            if (e instanceof Player player && (!player.isCreative() && (!player.isSpectator())))
                setTarget((LivingEntity) e);
        });
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getAttckingState() > 1)
            this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 100, false, false));
        this.summonTime++;
        if (this.summonTime >= 0 && this.getTarget() != null) {
            this.spawnWave(random.nextInt(0, 11), this.getTarget()); // Summons roughly every 2 minutes
            this.summonTime = -2400;
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        return source != damageSources().inWall() && source != damageSources().onFire() && source != damageSources().inFire() && super.hurt(
                source, amount);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        level().broadcastEntityEvent(this, (byte) 4);
        final var bl = target.hurt(damageSources().mobAttack(this),
                MCDoom.config.icon_melee_damage + (this.getDeathState() == 1 ? MCDoom.config.icon_phaseone_damage_boos : 0));
        if (bl) {
            level().explode(this, target.getX(), target.getY(), target.getZ(), 3.0F, false,
                    Level.ExplosionInteraction.BLOCK);
            doEnchantDamageEffects(this, target);
            target.invulnerableTime = 0;
        }
        return bl;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    @Override
    public void checkDespawn() {
    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    @Override
    public boolean fireImmune() {
        return true;
    }
}