package mod.azure.doom.entity.task;

import java.util.List;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import com.mojang.datafixers.util.Pair;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.tierambient.TurretEntity;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
import mod.azure.doom.entity.tierboss.GladiatorEntity;
import mod.azure.doom.entity.tierboss.IconofsinEntity;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import mod.azure.doom.entity.tierboss.SpiderMastermind2016Entity;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import mod.azure.doom.entity.tierfodder.ChaingunnerEntity;
import mod.azure.doom.entity.tierfodder.GargoyleEntity;
import mod.azure.doom.entity.tierfodder.ImpEntity;
import mod.azure.doom.entity.tierfodder.MaykrDroneEntity;
import mod.azure.doom.entity.tierfodder.MechaZombieEntity;
import mod.azure.doom.entity.tierfodder.PossessedScientistEntity;
import mod.azure.doom.entity.tierfodder.PossessedSoldierEntity;
import mod.azure.doom.entity.tierfodder.ShotgunguyEntity;
import mod.azure.doom.entity.tierfodder.ZombiemanEntity;
import mod.azure.doom.entity.tierheavy.ArachnotronEntity;
import mod.azure.doom.entity.tierheavy.BloodMaykrEntity;
import mod.azure.doom.entity.tierheavy.CacodemonEntity;
import mod.azure.doom.entity.tierheavy.HellknightEntity;
import mod.azure.doom.entity.tierheavy.MancubusEntity;
import mod.azure.doom.entity.tierheavy.PainEntity;
import mod.azure.doom.entity.tierheavy.ProwlerEntity;
import mod.azure.doom.entity.tierheavy.Revenant2016Entity;
import mod.azure.doom.entity.tierheavy.RevenantEntity;
import mod.azure.doom.entity.tiersuperheavy.ArchvileEntity;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import mod.azure.doom.entity.tiersuperheavy.CyberdemonEntity;
import mod.azure.doom.entity.tiersuperheavy.DoomHunterEntity;
import mod.azure.doom.entity.tiersuperheavy.FireBaronEntity;
import mod.azure.doom.entity.tiersuperheavy.MarauderEntity;
import mod.azure.doom.entity.tiersuperheavy.SummonerEntity;
import mod.azure.doom.util.registry.DoomEntities;
import mod.azure.doom.util.registry.DoomSounds;
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

public class DemonProjectileAttack<E extends DemonEntity> extends CustomDelayedRangedBehaviour<E> {
	private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT));

	protected Function<E, Integer> attackIntervalSupplier = entity -> 20;
	protected Float damage = 0.0F;

	@Nullable
	protected LivingEntity target = null;

	public DemonProjectileAttack(int delayTicks) {
		super(delayTicks);
	}

	public DemonProjectileAttack<E> attackInterval(Function<E, Integer> supplier) {
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
	protected boolean checkExtraStartConditions(ServerLevel level, E entity) {
		this.target = BrainUtils.getTargetOfEntity(entity);

		return entity.getSensing().hasLineOfSight(this.target) && !entity.isWithinMeleeAttackRange(this.target);
	}

	@Override
	protected void start(E entity) {
		BehaviorUtils.lookAtEntity(entity, this.target);
	}

	@Override
	protected void stop(E entity) {
		this.target = null;
		entity.setAttackingState(0);
	}

	@Override
	protected void doDelayedAction(E entity) {
		BrainUtils.setForgettableMemory(entity, MemoryModuleType.ATTACK_COOLING_DOWN, true, this.attackIntervalSupplier.apply(entity));

		if (this.target == null)
			return;

		if (!entity.getSensing().hasLineOfSight(this.target) || entity.isWithinMeleeAttackRange(this.target))
			return;

		if (entity instanceof PossessedSoldierEntity || entity instanceof BaronEntity || entity instanceof FireBaronEntity)
			entity.shootBaron(this.target, damage, 0, 0, 0);

		if (entity instanceof MaykrDroneEntity)
			entity.shootBolt(this.target, damage);

		if (entity instanceof BloodMaykrEntity)
			entity.shootBloodBolt(this.target, damage);

		if (entity instanceof SpiderMastermindEntity || entity instanceof ChaingunnerEntity || entity instanceof ShotgunguyEntity || entity instanceof ZombiemanEntity) {
			final var aabb = entity.getBoundingBox().inflate(16);
			final var checkBlocking = TargetingConditions.forCombat().range(16).selector(target -> !target.getUseItem().is(Items.SHIELD));
			entity.level().getNearbyEntities(LivingEntity.class, checkBlocking, entity, aabb).stream().findFirst().ifPresent(target -> {
				BehaviorUtils.lookAtEntity(entity, target);
				target.hurt(entity.damageSources().mobAttack(entity), entity instanceof ChaingunnerEntity ? DoomMod.config.chaingun_bullet_damage : damage);
			});
		}

		if (entity instanceof SpiderMastermind2016Entity || entity instanceof ArachnotronEntity)
			entity.shootEnergyCell(this.target, damage);

		if (entity instanceof Revenant2016Entity || entity instanceof RevenantEntity) {
			entity.shootRocket(this.target, damage);
			entity.shootRocket(this.target, damage);
		}

		if (entity instanceof CyberdemonEntity)
			entity.shootRocket(this.target, damage);

		if (entity instanceof HellknightEntity || entity instanceof CacodemonEntity)
			entity.shootFireball(this.target, damage, 0);

		if (entity instanceof MechaZombieEntity || entity instanceof ImpEntity || entity instanceof GargoyleEntity || entity instanceof TurretEntity)
			entity.shootSmallFireball(this.target, damage);

		if (entity instanceof ProwlerEntity prowlerEntity) {
			prowlerEntity.shootSmallFireball(this.target, damage);
			prowlerEntity.teleport();
		}

		if (entity instanceof MarauderEntity marauderEntity) {
			final var aabb = marauderEntity.getBoundingBox().inflate(16);
			final var checkBlocking = TargetingConditions.forCombat().range(16).selector(target -> !target.getUseItem().is(Items.SHIELD));
			marauderEntity.level().getNearbyEntities(LivingEntity.class, checkBlocking, marauderEntity, aabb).stream().findFirst().ifPresent(target -> {
				BehaviorUtils.lookAtEntity(marauderEntity, target);
				target.hurt(marauderEntity.damageSources().mobAttack(marauderEntity), damage);
			});
		}

		if (entity instanceof ArchvileEntity archvileEntity) {
			archvileEntity.getCommandSenderWorld().getEntities(entity, new AABB(archvileEntity.blockPosition().above()).inflate(24D, 24D, 24D)).forEach(e -> {
				if (e instanceof Mob mob)
					mob.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1000, 1));
			});
//			archvileEntity.teleportRandomly();
			final double d = Math.min(this.target.getY(), archvileEntity.getY());
			final double e = Math.max(this.target.getY(), archvileEntity.getY()) + 1.0D;
			final float f = (float) Mth.atan2(this.target.getZ() - archvileEntity.getZ(), this.target.getX() - archvileEntity.getX());
			if (archvileEntity.distanceTo(target) < 8.0D) { // shoot flames
				for (var j = 0; j < 15; ++j) {
					float h = f + j * 3.1415927F * 0.4F;
					archvileEntity.spawnFlames(entity.getX() + Mth.cos(h) * 1.5D, entity.getZ() + Mth.sin(h) * 1.5D, d, e, h, 0);
				}

				for (var j = 0; j < 18; ++j) {
					float h = f + j * 3.1415927F * 2.0F / 8.0F + 1.2566371F;
					archvileEntity.spawnFlames(entity.getX() + Mth.cos(h) * 2.5D, entity.getZ() + Mth.sin(h) * 2.5D, d, e, h, 3);
				}
			} else // shoot ball
				for (var j = 0; j < 26; ++j) {
					final double l1 = 1.25D * (j + 1);
					archvileEntity.spawnFlames(entity.getX() + Mth.cos(f) * l1, entity.getZ() + Mth.sin(f) * l1, d, e, f, 32);
				}
		}

		if (entity instanceof SummonerEntity summonerEntity) {
			if (summonerEntity.getRandom().nextInt(0, 40) >= 17) // spawn flames
				for (var j = 0; j < 16; ++j)
					summonerEntity.spawnFlames(entity.getX() + Mth.cos((float) Mth.atan2(this.target.getZ() - entity.getZ(), this.target.getX() - summonerEntity.getX())) * (1.25D * (j + 1)), entity.getZ() + Mth.sin((float) Mth.atan2(this.target.getZ() - entity.getZ(), this.target.getX() - summonerEntity.getX())) * (1.25D * (j + 1)), Math.min(this.target.getY(), summonerEntity.getY()), Math.max(this.target.getY(), summonerEntity.getY()) + 1.0D,
							(float) Mth.atan2(this.target.getZ() - entity.getZ(), this.target.getX() - summonerEntity.getX()), 32);
			else // spawn wave
				summonerEntity.spawnWave();
		}

		if (entity instanceof MancubusEntity mancubusEntity) {
			if (mancubusEntity.distanceTo(target) < 8.0D && mancubusEntity.distanceTo(target) > 3.0D) // shoot flames
				mancubusEntity.shootMancubus(mancubusEntity, DoomMod.config.mancubus_ranged_damage);
			else // shoot ball
				mancubusEntity.shootBaron(mancubusEntity, DoomMod.config.mancubus_ranged_damage, 0, 0, 0);
		}

		if (entity instanceof MotherDemonEntity motherdemonEntity) {
			final var tentacleEntity = DoomEntities.TENTACLE.create(entity.level());
			tentacleEntity.moveTo(this.target.getX(), this.target.getY(), this.target.getZ(), 0, 0);
			entity.level().addFreshEntity(tentacleEntity);
			if (entity.getHealth() <= entity.getMaxHealth() * 0.50) { // summon flames
				for (var l = 0; l < 32; ++l) {
					final var f1 = (float) Mth.atan2(this.target.getZ() - entity.getZ(), this.target.getX() - entity.getX()) + l * (float) Math.PI * 0.4F;
					for (var y = 0; y < 5; ++y)
						motherdemonEntity.spawnFlames(this.target.getX() + (double) Mth.cos(f1) * this.target.getRandom().nextDouble() * 1.5D, this.target.getZ() + (double) Mth.sin(f1) * this.target.getRandom().nextDouble() * 1.5D, Math.min(this.target.getY(), this.target.getY()), Math.max(this.target.getY(), this.target.getY()) + 1.0D, f1, 0);
				}
				this.target.setDeltaMovement(this.target.getDeltaMovement().multiply(0.4f, 1.4f, 0.4f));
			} else { // shoot fireballs
				entity.shootFireball(this.target, DoomMod.config.motherdemon_ranged_damage + (motherdemonEntity.getDeathState() == 1 ? DoomMod.config.motherdemon_phaseone_damage_boos : 0), 0);
				entity.shootFireball(this.target, DoomMod.config.motherdemon_ranged_damage + (motherdemonEntity.getDeathState() == 1 ? DoomMod.config.motherdemon_phaseone_damage_boos : 0), 3);
				entity.shootFireball(this.target, DoomMod.config.motherdemon_ranged_damage + (motherdemonEntity.getDeathState() == 1 ? DoomMod.config.motherdemon_phaseone_damage_boos : 0), -3);
			}
		}

		if (entity instanceof PossessedScientistEntity)
			entity.throwPotion(this.target);

		if (entity instanceof DoomHunterEntity doomHunterEntity) {
			if (doomHunterEntity.getDeathState() == 1)
				for (int l = 0; l < 16; ++l)
					doomHunterEntity.spawnFlames(doomHunterEntity.getX() + Mth.cos((float) Mth.atan2(this.target.getZ() - doomHunterEntity.getZ(), this.target.getX() - doomHunterEntity.getX())) * 1.25D * (l + 1), doomHunterEntity.getZ() + Mth.sin((float) Mth.atan2(this.target.getZ() - doomHunterEntity.getZ(), this.target.getX() - doomHunterEntity.getX())) * 1.25D * (l + 1), Math.min(this.target.getY(), this.target.getY()), Math.max(this.target.getY(), this.target.getY()) + 1.0D,
							(float) Mth.atan2(this.target.getZ() - doomHunterEntity.getZ(), this.target.getX() - doomHunterEntity.getX()), l);
			if (doomHunterEntity.getDeathState() == 0) {
				doomHunterEntity.shootRocket(this.target, DoomMod.config.doomhunter_ranged_damage + (doomHunterEntity.getDeathState() == 1 ? DoomMod.config.doomhunter_extra_phase_two_damage : 0));
			}
		}

		if (entity instanceof PainEntity painEntity) {
			entity.playSound(DoomSounds.PAIN_HURT, 1.0F, 1.0F);
			var lost_soul = DoomEntities.LOST_SOUL.create(entity.level());
			lost_soul.moveTo(painEntity.getX(), painEntity.getY(), painEntity.getZ(), 0, 0);
			entity.level().addFreshEntity(lost_soul);
			if (painEntity.getVariant() == 2) { // if doom 64, summon another
				var lost_soul1 = DoomEntities.LOST_SOUL.create(entity.level());
				lost_soul1.moveTo(painEntity.getX(), painEntity.getY(), painEntity.getZ(), 0, 0);
				entity.level().addFreshEntity(lost_soul1);
			}
			entity.spawnLightSource(entity, entity.level().isWaterAt(entity.blockPosition()));
		}

		if (entity instanceof ArchMakyrEntity archMakyrEntity) {
			if (entity.getRandom().nextInt(0, 4) == 1) // shoot fireball
				entity.shootFireball(entity, DoomMod.config.archmaykr_ranged_damage + (archMakyrEntity.getDeathState() == 1 ? DoomMod.config.archmaykr_phaseone_damage_boost : archMakyrEntity.getDeathState() == 2 ? DoomMod.config.archmaykr_phasetwo_damage_boost : archMakyrEntity.getDeathState() == 3 ? DoomMod.config.archmaykr_phasethree_damage_boost : archMakyrEntity.getDeathState() == 4 ? DoomMod.config.archmaykr_phasefour_damage_boost : 0), 0);
			else { // summon flames
				for (var i = 1; i < 5; ++i) {
					var f1 = (float) Mth.atan2(this.target.getZ() - entity.getZ(), this.target.getX() - entity.getX()) + (float) i * (float) Math.PI * 0.4F;
					for (var y = 0; y < 5; ++y)
						archMakyrEntity.spawnFlames(this.target.getX() + (double) Mth.cos(f1) * this.target.getRandom().nextDouble() * 1.5D, this.target.getZ() + (double) Mth.sin(f1) * this.target.getRandom().nextDouble() * 1.5D, Math.min(this.target.getY(), this.target.getY()), Math.max(this.target.getY(), this.target.getY()) + 1.0D, f1, 0);
				}
				this.target.setDeltaMovement(this.target.getDeltaMovement().multiply(0.4f, 1.4f, 0.4f));
			}
		}

		if (entity instanceof IconofsinEntity iconEntity) {
			int randomAttack = entity.getRandom().nextInt(0, 4);
			if (randomAttack == 1) {// Summon Fire on target
				for (var i = 1; i < 5; ++i) {
					var f1 = (float) Mth.atan2(this.target.getZ() - entity.getZ(), this.target.getX() - entity.getX()) + (float) i * (float) Math.PI * 0.4F;
					for (var y = 0; y < 5; ++y)
						iconEntity.spawnFlames(this.target.getX() + (double) Mth.cos(f1) * this.target.getRandom().nextDouble() * 1.5D, this.target.getZ() + (double) Mth.sin(f1) * this.target.getRandom().nextDouble() * 1.5D, Math.min(this.target.getY(), this.target.getY()), Math.max(this.target.getY(), this.target.getY()) + 1.0D, f1, 0);
				}
				if (entity.getHealth() < (entity.getMaxHealth() * 0.50))
					entity.setAttackingState(6); // no armor
				else
					entity.setAttackingState(5); // armor
			} else { // shoots fireball
				entity.shootFireball(entity, DoomMod.config.icon_melee_damage + (iconEntity.getDeathState() == 1 ? DoomMod.config.icon_phaseone_damage_boos : 0), 0);
				if (entity.getHealth() < (entity.getMaxHealth() * 0.50))
					entity.setAttackingState(2); // no armor
				else
					entity.setAttackingState(1); // armor
			}
		}

		if (entity instanceof GladiatorEntity gladiatorEntity) {
			if (gladiatorEntity.getDeathState() == 0) {
				var areaeffectcloudentity = new AreaEffectCloud(gladiatorEntity.level(), gladiatorEntity.getX(), gladiatorEntity.getY(), gladiatorEntity.getZ());
				areaeffectcloudentity.setParticle(ParticleTypes.SOUL_FIRE_FLAME);
				areaeffectcloudentity.setRadius(3.0F);
				areaeffectcloudentity.setDuration(10);
				areaeffectcloudentity.setPos(gladiatorEntity.getX(), gladiatorEntity.getY(), gladiatorEntity.getZ());
				entity.level().addFreshEntity(areaeffectcloudentity);
				gladiatorEntity.shootFireball(this.target, DoomMod.config.gladiator_ranged_damage + (gladiatorEntity.getDeathState() == 1 ? DoomMod.config.gladiator_phaseone_damage_boost : 0), 0);
			} else
				gladiatorEntity.shootMace(this.target);
		}
	}

}
