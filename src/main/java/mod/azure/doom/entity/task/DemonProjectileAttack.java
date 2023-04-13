package mod.azure.doom.entity.task;

import java.util.List;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import com.mojang.datafixers.util.Pair;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
import mod.azure.doom.entity.tierboss.ArchMakyrEntity;
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
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import mod.azure.doom.entity.tiersuperheavy.CyberdemonEntity;
import mod.azure.doom.entity.tiersuperheavy.FireBaronEntity;
import mod.azure.doom.entity.tiersuperheavy.MarauderEntity;
import mod.azure.doom.util.registry.DoomEntities;
import mod.azure.doom.util.registry.DoomSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
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

		if (entity instanceof SpiderMastermindEntity || entity instanceof ChaingunnerEntity || entity instanceof ShotgunguyEntity || entity instanceof ZombiemanEntity || entity instanceof MarauderEntity)
			entity.shootChaingun(this.target, entity instanceof ChaingunnerEntity ? DoomConfig.chaingun_bullet_damage : damage);

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

		if (entity instanceof MechaZombieEntity || entity instanceof ImpEntity || entity instanceof GargoyleEntity)
			entity.shootSmallFireball(this.target, damage);

		if (entity instanceof ProwlerEntity prowlerEntity) {
			prowlerEntity.shootSmallFireball(this.target, damage);
			prowlerEntity.teleport();
		}

		if (entity instanceof MancubusEntity mancubusEntity) {
			if (mancubusEntity.distanceTo(target) < 6.0D && mancubusEntity.distanceTo(target) > 3.0D)
				mancubusEntity.shootMancubus(mancubusEntity, DoomConfig.mancubus_ranged_damage);
			else 
				mancubusEntity.shootBaron(mancubusEntity, DoomConfig.mancubus_ranged_damage, 0, 0, 0);
		}

		if (entity instanceof MotherDemonEntity motherdemonEntity) {
			final var tentacleEntity = DoomEntities.TENTACLE.create(entity.level);
			tentacleEntity.moveTo(this.target.getX(), this.target.getY(), this.target.getZ(), 0, 0);
			entity.level.addFreshEntity(tentacleEntity);
			if (entity.getHealth() <= entity.getMaxHealth() * 0.50) {
				for (var l = 0; l < 32; ++l) {
					final var f1 = (float) Mth.atan2(this.target.getZ() - entity.getZ(), this.target.getX() - entity.getX()) + l * (float) Math.PI * 0.4F;
					for (var y = 0; y < 5; ++y)
						motherdemonEntity.spawnFlames(this.target.getX() + (double) Mth.cos(f1) * this.target.getRandom().nextDouble() * 1.5D, this.target.getZ() + (double) Mth.sin(f1) * this.target.getRandom().nextDouble() * 1.5D, Math.min(this.target.getY(), this.target.getY()), Math.max(this.target.getY(), this.target.getY()) + 1.0D, f1, 0);
				}
				this.target.setDeltaMovement(this.target.getDeltaMovement().multiply(0.4f, 1.4f, 0.4f));
			} else {
				entity.shootFireball(this.target, DoomConfig.motherdemon_ranged_damage + (motherdemonEntity.getDeathState() == 1 ? DoomConfig.motherdemon_phaseone_damage_boos : 0), 0);
				entity.shootFireball(this.target, DoomConfig.motherdemon_ranged_damage + (motherdemonEntity.getDeathState() == 1 ? DoomConfig.motherdemon_phaseone_damage_boos : 0), 3);
				entity.shootFireball(this.target, DoomConfig.motherdemon_ranged_damage + (motherdemonEntity.getDeathState() == 1 ? DoomConfig.motherdemon_phaseone_damage_boos : 0), -3);
			}
		}

		if (entity instanceof PossessedScientistEntity)
			entity.throwPotion(this.target);

		if (entity instanceof PainEntity painEntity) {
			entity.playSound(DoomSounds.PAIN_HURT, 1.0F, 1.0F);
			var lost_soul = DoomEntities.LOST_SOUL.create(entity.level);
			lost_soul.moveTo(painEntity.getX(), painEntity.getY(), painEntity.getZ(), 0, 0);
			entity.level.addFreshEntity(lost_soul);
			if (painEntity.getVariant() == 2) {
				var lost_soul1 = DoomEntities.LOST_SOUL.create(entity.level);
				lost_soul1.moveTo(painEntity.getX(), painEntity.getY(), painEntity.getZ(), 0, 0);
				entity.level.addFreshEntity(lost_soul1);
			}
			entity.spawnLightSource(entity, entity.level.isWaterAt(entity.blockPosition()));
		}

		if (entity instanceof ArchMakyrEntity archMakyrEntity) {
			if (entity.getRandom().nextInt(0, 4) == 1)
				entity.shootFireball(entity, DoomConfig.archmaykr_ranged_damage + (archMakyrEntity.getDeathState() == 1 ? DoomConfig.archmaykr_phaseone_damage_boost : archMakyrEntity.getDeathState() == 2 ? DoomConfig.archmaykr_phasetwo_damage_boost : archMakyrEntity.getDeathState() == 3 ? DoomConfig.archmaykr_phasethree_damage_boost : archMakyrEntity.getDeathState() == 4 ? DoomConfig.archmaykr_phasefour_damage_boost : 0), 0);
			else {
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
					for (var y = 0; y < 5; ++y) {
						iconEntity.spawnFlames(this.target.getX() + (double) Mth.cos(f1) * this.target.getRandom().nextDouble() * 1.5D, this.target.getZ() + (double) Mth.sin(f1) * this.target.getRandom().nextDouble() * 1.5D, Math.min(this.target.getY(), this.target.getY()), Math.max(this.target.getY(), this.target.getY()) + 1.0D, f1, 0);
					}
				}
				if (entity.getHealth() < (entity.getMaxHealth() * 0.50))
					entity.setAttackingState(6); // no armor
				else
					entity.setAttackingState(5); // armor
			} else { // shoots fireball
				entity.shootFireball(entity, DoomConfig.icon_melee_damage + (iconEntity.getDeathState() == 1 ? DoomConfig.motherdemon_phaseone_damage_boos : 0), 0);
				if (entity.getHealth() < (entity.getMaxHealth() * 0.50))
					entity.setAttackingState(2); // no armor
				else
					entity.setAttackingState(1); // armor
			}
		}
	}

}
