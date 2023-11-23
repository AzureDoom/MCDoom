package mod.azure.doom.entities.task;

import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.entities.tierboss.GladiatorEntity;
import mod.azure.doom.entities.tierboss.IconofsinEntity;
import mod.azure.doom.entities.tierheavy.Hellknight2016Entity;
import mod.azure.doom.entities.tierheavy.MancubusEntity;
import mod.azure.doom.entities.tiersuperheavy.DoomHunterEntity;
import mod.azure.doom.entities.tiersuperheavy.MarauderEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.phys.Vec3;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;

import java.util.function.Consumer;

public abstract class CustomDelayedMeleeBehaviour<E extends DemonEntity> extends ExtendedBehaviour<E> {
    protected final int delayTime;
    protected long delayFinishedAt = 0;
    protected Consumer<E> delayedCallback = entity -> {
    };

    protected CustomDelayedMeleeBehaviour(int delayTicks) {
        this.delayTime = delayTicks;

        runFor(entity -> Math.max(delayTicks, 60));
    }

    /**
     * A callback for when the delayed action is called.
     *
     * @param callback The callback
     * @return this
     */
    public final CustomDelayedMeleeBehaviour<E> whenActivating(Consumer<E> callback) {
        this.delayedCallback = callback;

        return this;
    }

    @Override
    protected final void start(ServerLevel level, E entity, long gameTime) {
        if (this.delayTime > 0) {
            this.delayFinishedAt = gameTime + this.delayTime;
            super.start(level, entity, gameTime);
        } else {
            super.start(level, entity, gameTime);
            doDelayedAction(entity);
        }
        entity.triggerAnim("attackController", "melee");
        if (!(entity instanceof MarauderEntity)) entity.triggerAnim("livingController", "melee");
        if (entity instanceof MarauderEntity marauder) {
            marauder.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 100, false, false));
            marauder.triggerAnim("attackController", switch (marauder.getRandom().nextInt(3)) {
                case 0 -> "cut";
                case 1 -> "cut";
                case 2 -> "hook";
                default -> "cut";
            });
        }
        if (entity instanceof MancubusEntity mancubusEntity) mancubusEntity.setAttackingState(3);
        if (entity instanceof DoomHunterEntity doomHunterEntity) doomHunterEntity.setAttackingState(3);

        if (entity instanceof GladiatorEntity gladiatorEntity) {
            if (gladiatorEntity.getDeathState() == 0)
                gladiatorEntity.triggerAnim("attackController", switch (gladiatorEntity.getRandom().nextInt(2)) {
                    case 0 -> "meleeone";
                    case 1 -> "meleetwo";
                    default -> "meleeone";
                });
            else gladiatorEntity.triggerAnim("attackController", switch (gladiatorEntity.getRandom().nextInt(2)) {
                case 0 -> "melee2one";
                case 1 -> "melee2two";
                default -> "melee2one";
            });
            gladiatorEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 100, false, false));
        }

        if (entity instanceof IconofsinEntity iconEntity)
            if (iconEntity.getDeathState() == 0) iconEntity.triggerAnim("attackController", "phaseonestomp");
            else iconEntity.triggerAnim("attackController", "phasetwostomp");

        if (!(entity instanceof IconofsinEntity) || !(entity instanceof MancubusEntity) || !(entity instanceof Hellknight2016Entity) || !(entity instanceof DoomHunterEntity))
            entity.setAttackingState(2);
        if (entity instanceof MarauderEntity marauderEntity)
            marauderEntity.triggerAnim("livingController", "attacking");
        if (entity instanceof Hellknight2016Entity hellknight2016) {
            hellknight2016.triggerAnim("livingController", "attack");
            if (hellknight2016.getTarget() != null && !hellknight2016.level().isClientSide()) {
                var vec3d2 = new Vec3(hellknight2016.getTarget().getX() - hellknight2016.getX(), 0.0, hellknight2016.getTarget().getZ() - hellknight2016.getZ());
                vec3d2 = vec3d2.normalize().scale(0.8).add(hellknight2016.getDeltaMovement().scale(0.4));
                hellknight2016.setDeltaMovement(vec3d2.x, 0.6F, vec3d2.z);
            }
        }
    }

    @Override
    protected final void stop(ServerLevel level, E entity, long gameTime) {
        super.stop(level, entity, gameTime);

        this.delayFinishedAt = 0;
        entity.setAttackingState(0);
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        return this.delayFinishedAt >= entity.level().getGameTime();
    }

    @Override
    protected final void tick(ServerLevel level, E entity, long gameTime) {
        super.tick(level, entity, gameTime);

        if (this.delayFinishedAt <= gameTime) {
            doDelayedAction(entity);
            this.delayedCallback.accept(entity);
        }
    }

    /**
     * The action to take once the delay period has elapsed.
     *
     * @param entity The owner of the brain
     */
    protected void doDelayedAction(E entity) {
    }
}
