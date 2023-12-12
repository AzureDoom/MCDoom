package mod.azure.doom.entities.task;

import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.entities.tierboss.GladiatorEntity;
import mod.azure.doom.entities.tierboss.IconofsinEntity;
import mod.azure.doom.entities.tierheavy.MancubusEntity;
import mod.azure.doom.entities.tiersuperheavy.ArchvileEntity;
import mod.azure.doom.entities.tiersuperheavy.DoomHunterEntity;
import mod.azure.doom.entities.tiersuperheavy.MarauderEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public abstract class CustomDelayedRangedBehaviour<E extends DemonEntity> extends ExtendedBehaviour<E> {
    protected final int delayTime;
    protected long delayFinishedAt = 0;
    protected Consumer<E> delayedCallback = entity -> {
    };

    protected CustomDelayedRangedBehaviour(int delayTicks) {
        this.delayTime = delayTicks;

        runFor(entity -> Math.max(delayTicks, 60));
    }

    @Override
    protected final void start(@NotNull ServerLevel level, @NotNull E entity, long gameTime) {
        if (this.delayTime > 0) {
            this.delayFinishedAt = gameTime + this.delayTime;
            super.start(level, entity, gameTime);
        } else {
            super.start(level, entity, gameTime);
            doDelayedAction(entity);
        }
        if (entity instanceof MarauderEntity marauder) {
            marauder.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 100, false, false));
            marauder.triggerAnim("attackController", marauder.getRandom().nextInt(3) == 1 ? "slash" : "ranged");
        }
        if (entity instanceof ArchvileEntity)
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 100, false, false));
        if (entity instanceof MancubusEntity mancubusEntity) mancubusEntity.setAttackingState(2);
        else if (entity instanceof GladiatorEntity gladiatorEntity) {
            if (gladiatorEntity.getDeathState() == 0) {
                entity.triggerAnim("attackController", "ranged");
                gladiatorEntity.setTextureState(2);
            } else entity.triggerAnim("attackController", "mace");
            gladiatorEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 100, false, false));
        } else entity.setAttackingState(1);
        if (!(entity instanceof MarauderEntity)) entity.triggerAnim("livingController", "ranged");
        if (!(entity instanceof DoomHunterEntity || entity instanceof GladiatorEntity || entity instanceof MarauderEntity))
            entity.triggerAnim("attackController", "ranged");
        if (entity instanceof DoomHunterEntity hunter) {
            if (hunter.getDeathState() == 1) hunter.triggerAnim("attackController", "flames");
            if (hunter.getDeathState() == 0) hunter.triggerAnim("attackController", "rocket");
        }
        if (entity instanceof IconofsinEntity iconEntity)
            if (iconEntity.getDeathState() == 0) iconEntity.triggerAnim("attackController", "phaseoneranged");
            else iconEntity.triggerAnim("attackController", "phasetworanged");
    }

    @Override
    protected final void stop(@NotNull ServerLevel level, @NotNull E entity, long gameTime) {
        super.stop(level, entity, gameTime);

        this.delayFinishedAt = 0;
        if (entity instanceof GladiatorEntity gladiatorEntity) gladiatorEntity.setTextureState(0);
    }

    @Override
    protected boolean shouldKeepRunning(E entity) {
        return this.delayFinishedAt >= entity.level().getGameTime();
    }

    @Override
    protected final void tick(@NotNull ServerLevel level, @NotNull E entity, long gameTime) {
        super.tick(level, entity, gameTime);

        if (this.delayFinishedAt <= gameTime) {
            doDelayedAction(entity);
            this.delayedCallback.accept(entity);
        }
        entity.getNavigation().stop();
    }

    /**
     * The action to take once the delay period has elapsed.
     *
     * @param entity The owner of the brain
     */
    protected void doDelayedAction(E entity) {
    }
}
