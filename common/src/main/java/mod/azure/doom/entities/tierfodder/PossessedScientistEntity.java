package mod.azure.doom.entities.tierfodder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.azurelib.animatable.GeoEntity;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager.ControllerRegistrar;
import mod.azure.azurelib.core.animation.AnimationController;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.entities.DoomAnimationsDefault;
import mod.azure.doom.entities.task.DemonMeleeAttack;
import mod.azure.doom.entities.task.DemonProjectileAttack;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.tslat.smartbrainlib.api.SmartBrainOwner;
import net.tslat.smartbrainlib.api.core.BrainActivityGroup;
import net.tslat.smartbrainlib.api.core.SmartBrainProvider;
import net.tslat.smartbrainlib.api.core.behaviour.FirstApplicableBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.OneRandomBehaviour;
import net.tslat.smartbrainlib.api.core.behaviour.custom.look.LookAtTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.misc.Idle;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.FloatToSurfaceOfFluid;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.MoveToWalkTarget;
import net.tslat.smartbrainlib.api.core.behaviour.custom.move.StrafeTarget;
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

public class PossessedScientistEntity extends DemonEntity implements GeoEntity, SmartBrainOwner<PossessedScientistEntity> {

    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public PossessedScientistEntity(EntityType<PossessedScientistEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.MAX_HEALTH, MCDoom.config.possessed_scientist_health).add(Attributes.ATTACK_DAMAGE, MCDoom.config.possessed_scientist_melee_damage).add(Attributes.MOVEMENT_SPEED, 0.15D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        var isDead = this.dead || this.getHealth() < 0.01 || this.isDeadOrDying();
        controllers.add(new AnimationController<>(this, "livingController", 0, event -> {
            if (event.isMoving() && !isDead && !this.swinging)
                return event.setAndContinue(DoomAnimationsDefault.WALK);
            return event.setAndContinue(isDead ? DoomAnimationsDefault.DEATH2 : DoomAnimationsDefault.IDLE);
        }).setSoundKeyframeHandler(event -> {
            if (event.getKeyframeData().getSound().matches("walk"))
                if (level().isClientSide())
                    level().playLocalSound(this.getX(), this.getY(), this.getZ(), mod.azure.doom.platform.Services.SOUNDS_HELPER.getPINKY_STEP(), SoundSource.HOSTILE, 0.25F, 1.0F, false);
        })).add(new AnimationController<>(this, "attackController", 0, event -> {
            return PlayState.STOP;
        }).triggerableAnim("ranged", DoomAnimationsDefault.ATTACK).triggerableAnim("melee", DoomAnimationsDefault.ATTACK));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void tickDeath() {
        ++deathTime;
        if (deathTime == 40) {
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
    public List<ExtendedSensor<PossessedScientistEntity>> getSensors() {
        return ObjectArrayList.of(new NearbyLivingEntitySensor<PossessedScientistEntity>().setPredicate((target, entity) -> target.isAlive() && entity.hasLineOfSight(target) && !(target instanceof DemonEntity)), new HurtBySensor<>(), new UnreachableTargetSensor<PossessedScientistEntity>());
    }

    @Override
    public BrainActivityGroup<PossessedScientistEntity> getCoreTasks() {
        return BrainActivityGroup.coreTasks(new LookAtTarget<>(), new LookAtTargetSink(40, 300), new FloatToSurfaceOfFluid<>(), new StrafeTarget<>().speedMod(0.95F), new MoveToWalkTarget<>());
    }

    @Override
    public BrainActivityGroup<PossessedScientistEntity> getIdleTasks() {
        return BrainActivityGroup.idleTasks(new FirstApplicableBehaviour<PossessedScientistEntity>(new TargetOrRetaliate<>().alertAlliesWhen((mob, entity) -> this.isAggressive()), new SetPlayerLookTarget<>().stopIf(target -> !target.isAlive() || target instanceof Player player && player.isCreative()), new SetRandomLookTarget<>()), new OneRandomBehaviour<>(new SetRandomWalkTarget<>().setRadius(20).speedModifier(0.7f), new Idle<>().runFor(entity -> entity.getRandom().nextInt(300, 600))));
    }

    @Override
    public BrainActivityGroup<PossessedScientistEntity> getFightTasks() {
        return BrainActivityGroup.fightTasks(new InvalidateAttackTarget<>().invalidateIf((target, entity) -> !target.isAlive() || !entity.hasLineOfSight(target)), new SetWalkTargetToAttackTarget<>().speedMod((owner, target) -> 0.75F), new DemonProjectileAttack<>(5).attackInterval(mob -> 80), new DemonMeleeAttack<>(5));
    }

    @Override
    protected void registerGoals() {
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
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getZOMBIEMAN_HURT();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return mod.azure.doom.platform.Services.SOUNDS_HELPER.getZOMBIEMAN_DEATH();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 7;
    }

}