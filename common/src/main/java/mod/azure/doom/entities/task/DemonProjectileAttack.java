package mod.azure.doom.entities.task;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.DemonEntity;
import mod.azure.doom.entities.tierambient.TurretEntity;
import mod.azure.doom.entities.tierboss.*;
import mod.azure.doom.entities.tierfodder.*;
import mod.azure.doom.entities.tierheavy.*;
import mod.azure.doom.entities.tiersuperheavy.*;
import mod.azure.doom.helper.CommonUtils;
import mod.azure.doom.platform.Services;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.tslat.smartbrainlib.util.BrainUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.ToIntFunction;

public class DemonProjectileAttack<E extends DemonEntity> extends CustomDelayedRangedBehaviour<E> {
    private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(
            Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT),
            Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT));

    protected ToIntFunction<E> attackIntervalSupplier = entity -> 20;
    protected Float damage = 0.0F;

    @Nullable
    protected LivingEntity target = null;

    public DemonProjectileAttack(int delayTicks) {
        super(delayTicks);
    }

    public DemonProjectileAttack<E> attackInterval(ToIntFunction<E> supplier) {
        this.attackIntervalSupplier = supplier;

        return this;
    }

    public DemonProjectileAttack<E> attackDamage(float damage) {
        this.damage = damage;
        return this;
    }

    @Override
    protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
        return MEMORY_REQUIREMENTS;
    }

    @Override
    protected boolean checkExtraStartConditions(@NotNull ServerLevel level, @NotNull E entity) {
        this.target = BrainUtils.getTargetOfEntity(entity);

        assert this.target != null;
        return entity.getSensing().hasLineOfSight(this.target) && !entity.isWithinMeleeAttackRange(this.target);
    }

    @Override
    protected void start(E entity) {
        assert this.target != null;
        BehaviorUtils.lookAtEntity(entity, this.target);
    }

    @Override
    protected void stop(E entity) {
        this.target = null;
        entity.setAttackingState(0);
    }

    @Override
    protected void doDelayedAction(E entity) {
        BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true,
                this.attackIntervalSupplier.applyAsInt(entity));

        if (this.target == null) return;

        if (!entity.getSensing().hasLineOfSight(this.target) || entity.isWithinMeleeAttackRange(this.target)) return;

        BehaviorUtils.lookAtEntity(entity, this.target);

        if (entity instanceof PossessedSoldierEntity || entity instanceof BaronEntity || entity instanceof FireBaronEntity)
            entity.shootBaron(damage, 0, 0, 0);

        if (entity instanceof MaykrDroneEntity) entity.shootBolt(damage);

        if (entity instanceof BloodMaykrEntity) entity.shootBloodBolt(damage);

        if (entity instanceof ChaingunnerEntity || entity instanceof ShotgunguyEntity || entity instanceof ZombiemanEntity)
            this.hitScanAttack(entity);

        if (entity instanceof SpiderMastermindEntity || entity instanceof SpiderMastermind2016Entity || entity instanceof ArachnotronEntity)
            entity.shootEnergyCell(damage);

        if (entity instanceof Revenant2016Entity || entity instanceof RevenantEntity) {
            entity.shootRocket(damage);
            entity.shootRocket(damage);
        }

        if (entity instanceof CyberdemonEntity) entity.shootRocket(damage);

        if (entity instanceof HellknightEntity || entity instanceof CacodemonEntity)
            entity.shootFireball(damage, 0);

        if (entity instanceof MechaZombieEntity || entity instanceof ImpEntity || entity instanceof GargoyleEntity || entity instanceof TurretEntity || entity instanceof CarcassEntity)
            entity.shootSmallFireball(damage);

        if (entity instanceof ProwlerEntity prowlerEntity) {
            prowlerEntity.shootSmallFireball(damage);
            prowlerEntity.teleport();
        }

        if (entity instanceof MarauderEntity marauderEntity) this.marauderAttacks(marauderEntity);

        if (entity instanceof ArchvileEntity archvileEntity) this.archvileAttacks(archvileEntity);

        if (entity instanceof SummonerEntity summonerEntity) {
            if (summonerEntity.getRandom().nextInt(0, 40) >= 17) // spawn flames
                for (var j = 0; j < 16; ++j)
                    summonerEntity.spawnFlames(entity.getX() + Mth.cos(
                                    (float) Mth.atan2(this.target.getZ() - entity.getZ(),
                                            this.target.getX() - summonerEntity.getX())) * (1.25D * (j + 1)),
                            entity.getZ() + Mth.sin((float) Mth.atan2(this.target.getZ() - entity.getZ(),
                                    this.target.getX() - summonerEntity.getX())) * (1.25D * (j + 1)),
                            Math.min(this.target.getY(), summonerEntity.getY()),
                            Math.max(this.target.getY(), summonerEntity.getY()) + 1.0D,
                            (float) Mth.atan2(this.target.getZ() - entity.getZ(),
                                    this.target.getX() - summonerEntity.getX()));
            else // spawn wave
                summonerEntity.spawnWave();
        }

        if (entity instanceof MancubusEntity mancubusEntity) {
            if (mancubusEntity.distanceTo(target) < 8.0D && mancubusEntity.distanceTo(target) > 3.0D) // shoot flames
                mancubusEntity.shootMancubus(MCDoom.config.mancubus_ranged_damage);
            else // shoot ball
                mancubusEntity.shootBaron(MCDoom.config.mancubus_ranged_damage, 0, 0, 0);
        }

        if (entity instanceof MotherDemonEntity motherdemonEntity) this.motherDemonAttacks(motherdemonEntity);

        if (entity instanceof PossessedScientistEntity) entity.throwPotion(this.target);

        if (entity instanceof DoomHunterEntity doomHunterEntity) this.doomHunterAttacks(doomHunterEntity);

        if (entity instanceof PainEntity painEntity) this.painAttacks(painEntity);

        if (entity instanceof ArchMakyrEntity archMakyrEntity) this.archMakyrAttacks(archMakyrEntity);

        if (entity instanceof IconofsinEntity iconEntity) this.iconAttacks(iconEntity);

        if (entity instanceof GladiatorEntity gladiatorEntity) this.gladiatorAttacks(gladiatorEntity);
    }

    private void hitScanAttack(LivingEntity livingEntity) {
        final var aabb = livingEntity.getBoundingBox().inflate(16);
        final var checkBlocking = TargetingConditions.forCombat().range(16).selector(
                target1 -> !target1.getUseItem().is(Items.SHIELD));
        livingEntity.level().getNearbyEntities(LivingEntity.class, checkBlocking, livingEntity,
                aabb).stream().findFirst().ifPresent(target2 -> {
            BehaviorUtils.lookAtEntity(livingEntity, target2);
            target2.hurt(livingEntity.damageSources().mobAttack(livingEntity),
                    livingEntity instanceof ChaingunnerEntity ? MCDoom.config.chaingun_bullet_damage : damage);
        });
    }

    private void marauderAttacks(MarauderEntity marauderEntity) {
        final var aabb = marauderEntity.getBoundingBox().inflate(16);
        final var checkBlocking = TargetingConditions.forCombat().range(16).selector(
                target1 -> !target1.getUseItem().is(Items.SHIELD));
        marauderEntity.level().getNearbyEntities(LivingEntity.class, checkBlocking, marauderEntity,
                aabb).stream().findFirst().ifPresent(target2 -> {
            BehaviorUtils.lookAtEntity(marauderEntity, target2);
            target2.hurt(marauderEntity.damageSources().mobAttack(marauderEntity), damage);
        });
    }

    private void archvileAttacks(ArchvileEntity archvileEntity) {
        archvileEntity.level().getEntities(archvileEntity,
                new AABB(archvileEntity.blockPosition().above()).inflate(24D, 24D, 24D)).forEach(e -> {
            if (e instanceof Mob mob) mob.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000, 1));
        });
        assert this.target != null;
        final double d = Math.min(this.target.getY(), archvileEntity.getY());
        final double e = Math.max(this.target.getY(), archvileEntity.getY()) + 1.0D;
        final float f = (float) Mth.atan2(this.target.getZ() - archvileEntity.getZ(),
                this.target.getX() - archvileEntity.getX());
        if (archvileEntity.distanceTo(target) < 8.0D) { // shoot flames
            for (var j = 0; j < 15; ++j) {
                float h = f + j * 3.1415927F * 0.4F;
                archvileEntity.spawnFlames(archvileEntity.getX() + Mth.cos(h) * 1.5D,
                        archvileEntity.getZ() + Mth.sin(h) * 1.5D, d, e, h);
            }

            for (var j = 0; j < 18; ++j) {
                float h = f + j * 3.1415927F * 2.0F / 8.0F + 1.2566371F;
                archvileEntity.spawnFlames(archvileEntity.getX() + Mth.cos(h) * 2.5D,
                        archvileEntity.getZ() + Mth.sin(h) * 2.5D, d, e, h);
            }
        } else // shoot ball
            for (var j = 0; j < 26; ++j) {
                final double l1 = 1.25D * (j + 1);
                archvileEntity.spawnFlames(archvileEntity.getX() + Mth.cos(f) * l1,
                        archvileEntity.getZ() + Mth.sin(f) * l1, d, e, f);
            }
    }

    private void motherDemonAttacks(MotherDemonEntity motherdemonEntity) {
        final var tentacleEntity = Services.ENTITIES_HELPER.getTentacleEntity().create(motherdemonEntity.level());
        assert tentacleEntity != null;
        assert this.target != null;
        tentacleEntity.moveTo(this.target.getX(), this.target.getY(), this.target.getZ(), 0, 0);
        motherdemonEntity.level().addFreshEntity(tentacleEntity);
        if (motherdemonEntity.getHealth() <= motherdemonEntity.getMaxHealth() * 0.50) { // summon flames
            for (var l = 0; l < 32; ++l) {
                final var f1 = (float) Mth.atan2(this.target.getZ() - motherdemonEntity.getZ(),
                        this.target.getX() - motherdemonEntity.getX()) + l * (float) Math.PI * 0.4F;
                for (var y = 0; y < 5; ++y)
                    motherdemonEntity.spawnFlames(
                            this.target.getX() + Mth.cos(f1) * this.target.getRandom().nextDouble() * 1.5D,
                            this.target.getZ() + Mth.sin(f1) * this.target.getRandom().nextDouble() * 1.5D,
                            Math.min(this.target.getY(), this.target.getY()),
                            Math.max(this.target.getY(), this.target.getY()) + 1.0D, f1);
            }
            this.target.setDeltaMovement(this.target.getDeltaMovement().multiply(0.4f, 1.4f, 0.4f));
        } else { // shoot fireballs
            motherdemonEntity.shootFireball(
                    MCDoom.config.motherdemon_ranged_damage + this.extraMotherDemonDamage(motherdemonEntity), 0);
            motherdemonEntity.shootFireball(
                    MCDoom.config.motherdemon_ranged_damage + this.extraMotherDemonDamage(motherdemonEntity), 3);
            motherdemonEntity.shootFireball(
                    MCDoom.config.motherdemon_ranged_damage + this.extraMotherDemonDamage(motherdemonEntity), -3);
        }
    }

    private float extraMotherDemonDamage(MotherDemonEntity motherDemonEntity) {
        if (motherDemonEntity.getDeathState() == 1) return MCDoom.config.motherdemon_phaseone_damage_boos;
        return 0;
    }

    private void doomHunterAttacks(DoomHunterEntity doomHunterEntity) {
        if (doomHunterEntity.getDeathState() == 1) {
            for (int l = 0; l < 16; ++l) {
                assert this.target != null;
                doomHunterEntity.spawnFlames(doomHunterEntity.getX() + Mth.cos(
                                (float) Mth.atan2(this.target.getZ() - doomHunterEntity.getZ(),
                                        this.target.getX() - doomHunterEntity.getX())) * 1.25D * (l + 1),
                        doomHunterEntity.getZ() + Mth.sin(
                                (float) Mth.atan2(this.target.getZ() - doomHunterEntity.getZ(),
                                        this.target.getX() - doomHunterEntity.getX())) * 1.25D * (l + 1),
                        Math.min(this.target.getY(), this.target.getY()),
                        Math.max(this.target.getY(), this.target.getY()) + 1.0D,
                        (float) Mth.atan2(this.target.getZ() - doomHunterEntity.getZ(),
                                this.target.getX() - doomHunterEntity.getX()));
            }
        }
        if (doomHunterEntity.getDeathState() == 0) {
            doomHunterEntity.shootRocket(
                    MCDoom.config.doomhunter_ranged_damage + (doomHunterEntity.getDeathState() == 1 ? MCDoom.config.doomhunter_extra_phase_two_damage : 0));
        }
    }

    private void painAttacks(PainEntity painEntity) {
        painEntity.playSound(Services.SOUNDS_HELPER.getPAIN_HURT(), 1.0F, 1.0F);
        var lostSoul = Services.ENTITIES_HELPER.getLostSoulEntity().create(painEntity.level());
        assert lostSoul != null;
        lostSoul.moveTo(painEntity.getX(), painEntity.getY(), painEntity.getZ(), 0, 0);
        painEntity.level().addFreshEntity(lostSoul);
        if (painEntity.getVariant() == 2) { // if doom 64, summon another
            var lostSoul1 = Services.ENTITIES_HELPER.getLostSoulEntity().create(painEntity.level());
            assert lostSoul1 != null;
            lostSoul1.moveTo(painEntity.getX(), painEntity.getY(), painEntity.getZ(), 0, 0);
            painEntity.level().addFreshEntity(lostSoul1);
        }
        CommonUtils.spawnLightSource(painEntity, painEntity.level().isWaterAt(painEntity.blockPosition()));
    }

    private void archMakyrAttacks(ArchMakyrEntity archMakyrEntity) {
        if (archMakyrEntity.getRandom().nextInt(0, 4) == 1) // shoot fireball
            archMakyrEntity.shootFireball(
                    MCDoom.config.archmaykr_ranged_damage + this.extraArchMaykerDamage(archMakyrEntity), 0);
        else { // summon flames
            for (var i = 1; i < 5; ++i) {
                assert this.target != null;
                var f1 = (float) Mth.atan2(this.target.getZ() - archMakyrEntity.getZ(),
                        this.target.getX() - archMakyrEntity.getX()) + i * (float) Math.PI * 0.4F;
                for (var y = 0; y < 5; ++y)
                    archMakyrEntity.spawnFlames(
                            this.target.getX() + Mth.cos(f1) * this.target.getRandom().nextDouble() * 1.5D,
                            this.target.getZ() + Mth.sin(f1) * this.target.getRandom().nextDouble() * 1.5D,
                            Math.min(this.target.getY(), this.target.getY()),
                            Math.max(this.target.getY(), this.target.getY()) + 1.0D, f1);
            }
            this.target.setDeltaMovement(this.target.getDeltaMovement().multiply(0.4f, 1.4f, 0.4f));
        }
    }

    private void iconAttacks(IconofsinEntity iconEntity) {
        int randomAttack = iconEntity.getRandom().nextInt(0, 4);
        if (randomAttack == 1) {// Summon Fire on target
            for (var i = 1; i < 5; ++i) {
                assert this.target != null;
                var f1 = (float) Mth.atan2(this.target.getZ() - iconEntity.getZ(),
                        this.target.getX() - iconEntity.getX()) + i * (float) Math.PI * 0.4F;
                for (var y = 0; y < 5; ++y)
                    iconEntity.spawnFlames(
                            this.target.getX() + Mth.cos(f1) * this.target.getRandom().nextDouble() * 1.5D,
                            this.target.getZ() + Mth.sin(f1) * this.target.getRandom().nextDouble() * 1.5D,
                            Math.min(this.target.getY(), this.target.getY()),
                            Math.max(this.target.getY(), this.target.getY()) + 1.0D, f1);
            }
            if (iconEntity.getHealth() < (iconEntity.getMaxHealth() * 0.50))
                iconEntity.setAttackingState(6); // no armor
            else iconEntity.setAttackingState(5); // armor
        } else { // shoots fireball
            iconEntity.shootFireball(
                    MCDoom.config.icon_melee_damage + (iconEntity.getDeathState() == 1 ? MCDoom.config.icon_phaseone_damage_boos : 0),
                    0);
            if (iconEntity.getHealth() < (iconEntity.getMaxHealth() * 0.50))
                iconEntity.setAttackingState(2); // no armor
            else iconEntity.setAttackingState(1); // armor
        }
    }

    private void gladiatorAttacks(GladiatorEntity gladiatorEntity) {
        if (gladiatorEntity.getDeathState() == 0) {
            var areaeffectcloudentity = new AreaEffectCloud(gladiatorEntity.level(), gladiatorEntity.getX(),
                    gladiatorEntity.getY(), gladiatorEntity.getZ());
            areaeffectcloudentity.setParticle(ParticleTypes.SOUL_FIRE_FLAME);
            areaeffectcloudentity.setRadius(3.0F);
            areaeffectcloudentity.setDuration(10);
            areaeffectcloudentity.setPos(gladiatorEntity.getX(), gladiatorEntity.getY(), gladiatorEntity.getZ());
            gladiatorEntity.level().addFreshEntity(areaeffectcloudentity);
            gladiatorEntity.shootFireball(
                    MCDoom.config.gladiator_ranged_damage + (gladiatorEntity.getDeathState() == 1 ? MCDoom.config.gladiator_phaseone_damage_boost : 0),
                    0);
        } else gladiatorEntity.shootMace();
    }

    private float extraArchMaykerDamage(ArchMakyrEntity archMakyrEntity) {
        if (archMakyrEntity.getDeathState() == 1) return MCDoom.config.archmaykr_phaseone_damage_boost;
        if (archMakyrEntity.getDeathState() == 2) return MCDoom.config.archmaykr_phasetwo_damage_boost;
        if (archMakyrEntity.getDeathState() == 3) return MCDoom.config.archmaykr_phasethree_damage_boost;
        if (archMakyrEntity.getDeathState() == 4) return MCDoom.config.archmaykr_phasefour_damage_boost;
        return 0;
    }

}
