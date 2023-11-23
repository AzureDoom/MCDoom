package mod.azure.doom.entities.tierfodder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.entities.DoomAnimationsDefault;
import mod.azure.doom.entities.ai.DemonFlyControl;
import mod.azure.doom.entities.task.DemonMeleeAttack;
import mod.azure.doom.entities.task.DemonProjectileAttack;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StayWithinDistanceOfAttackTarget;
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

import java.util.List;

public class GargoyleEntity extends DemonEntity implements SmartBrainOwner<GargoyleEntity> {

    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public GargoyleEntity(EntityType<GargoyleEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
        moveControl = new DemonFlyControl(this);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH, MCDoom.config.gargoyle_health).add(Attributes.ATTACK_DAMAGE, 3.0D).add(Attributes.FLYING_SPEED, 0.25D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        var isDead = this.isDeadOrDying();
        controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
            if (isDead)
                return event.setAndContinue(DoomAnimationsDefault.DEATH);
            if (!isAggressive() && event.isMoving() && !isDead)
                return event.setAndContinue(DoomAnimationsDefault.WALKING);
            if (isAggressive() && !this.swinging && event.isMoving() && !isDead)
                return event.setAndContinue(DoomAnimationsDefault.FLYING);
            if (!event.isCurrentAnimation(DoomAnimationsDefault.FLYING) && !isDead && !event.isCurrentAnimation(DoomAnimationsDefault.WALKING) && !event.isCurrentAnimation(DoomAnimationsDefault.ATTACKING))
                return event.setAndContinue(DoomAnimationsDefault.IDLE);
            return PlayState.CONTINUE;
        }).triggerableAnim("death", DoomAnimationsDefault.DEATH)).add(new AnimationController<>(this, "attackController", 0, event -> {
            return PlayState.STOP;
        }).triggerableAnim("death", DoomAnimationsDefault.DEATH).triggerableAnim("ranged", DoomAnimationsDefault.ATTACKING).triggerableAnim("melee", DoomAnimationsDefault.ATTACKING));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void tickDeath() {
        ++deathTime;
        this.triggerAnim("livingController", "death");
        this.triggerAnim("attackController", "death");
        if (deathTime == 50) {
            remove(RemovalReason.KILLED);
            dropExperience();
        }
    }

    @Override
    protected void customServerAiStep() {
        tickBrain(this);
        super.customServerAiStep();
    }

    @Override
    protected Brain.Provider<?> brainProvider() {
        return new SmartBrainProvider<>(this);
    }

    @Override
    public List<ExtendedSensor<GargoyleEntity>> getSensors() {
        return ObjectArrayList.of(new NearbyLivingEntitySensor<GargoyleEntity>().setPredicate((target, entity) -> target.isAlive() && entity.hasLineOfSight(target) && !(target instanceof DemonEntity)), new HurtBySensor<>(), new UnreachableTargetSensor<GargoyleEntity>());
    }

    @Override
    public BrainActivityGroup<GargoyleEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300), new FloatToSurfaceOfFluid<>(), new StayWithinDistanceOfAttackTarget<>().speedMod(0.25F), new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<GargoyleEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<GargoyleEntity>(new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()), new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player player && player.isCreative()), new SetRandomLookTarget<>()), new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(1.0f), new Idle<>().runFor(entity -> entity.getRandom().nextInt(300, 600))));
    }

    @Override
    public BrainActivityGroup<GargoyleEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().invalidateIf((target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)), new SetWalkTargetToAttackTarget<>().speedMod((owner, target) -> 1.05F), new DemonProjectileAttack<>(7).attackInterval(mob -> 80).attackDamage(MCDoom.config.gargoyle_ranged_damage), new DemonMeleeAttack<>(5));
    }

    @Override
    public double getMeleeAttackRangeSqr(LivingEntity entity) {
        return this.getBbWidth() * 1.5F * this.getBbWidth() * 1.5F + entity.getBbWidth();
    }

    @Override
    protected PathNavigation createNavigation(Level worldIn) {
        final var flyingpathnavigator = new FlyingPathNavigation(this, worldIn);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public void travel(Vec3 movementInput) {
        if (isAggressive()) {
            if (isInWater()) {
                moveRelative(0.02F, movementInput);
                move(MoverType.SELF, getDeltaMovement());
                this.setDeltaMovement(getDeltaMovement().scale(0.8F));
            } else if (isInLava()) {
                moveRelative(0.02F, movementInput);
                move(MoverType.SELF, getDeltaMovement());
                this.setDeltaMovement(getDeltaMovement().scale(0.5D));
            } else {
                final var ground = BlockPos.containing(this.getX(), this.getY() - 1.0D, this.getZ());
                var f = 0.91F;
                if (onGround())
                    f = level().getBlockState(ground).getBlock().getFriction() * 0.91F;
                final var f1 = 0.16277137F / (f * f * f);
                f = 0.91F;
                if (onGround())
                    f = level().getBlockState(ground).getBlock().getFriction() * 0.91F;
                moveRelative(onGround() ? 0.1F * f1 : 0.02F, movementInput);
                move(MoverType.SELF, getDeltaMovement());
                this.setDeltaMovement(getDeltaMovement().scale(f));
            }
        } else {
            super.travel(movementInput);
        }
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    protected boolean shouldDrown() {
        return false;
    }

    protected boolean shouldBurnInDay() {
        return false;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getGARGOLYE_HURT();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getGARGOLYE_DEATH();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 7;
    }

}