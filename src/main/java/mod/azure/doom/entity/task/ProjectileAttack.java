package mod.azure.doom.entity.task;

import java.util.List;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import com.mojang.datafixers.util.Pair;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.DemonEntity;
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
import mod.azure.doom.entity.tierheavy.ProwlerEntity;
import mod.azure.doom.entity.tierheavy.Revenant2016Entity;
import mod.azure.doom.entity.tierheavy.RevenantEntity;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import mod.azure.doom.entity.tiersuperheavy.CyberdemonEntity;
import mod.azure.doom.entity.tiersuperheavy.FireBaronEntity;
import mod.azure.doom.entity.tiersuperheavy.MarauderEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.util.BrainUtils;

public class ProjectileAttack<E extends DemonEntity> extends CustomDelayedBehaviour<E> {
	private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.VALUE_ABSENT));

	protected Function<E, Integer> attackIntervalSupplier = entity -> 20;
	protected Float damage = 0.0F;

	@Nullable
	protected LivingEntity target = null;

	public ProjectileAttack(int delayTicks) {
		super(delayTicks);
	}

	public ProjectileAttack<E> attackInterval(Function<E, Integer> supplier) {
		this.attackIntervalSupplier = supplier;

		return this;
	}

	public ProjectileAttack<E> attackDamage(float damage) {
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

		return entity.getSensing().hasLineOfSight(this.target);
	}

	@Override
	protected void start(E entity) {
		entity.swing(InteractionHand.MAIN_HAND);
		BehaviorUtils.lookAtEntity(entity, this.target);
		entity.setAttackingState(0);
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

		if (!entity.getSensing().hasLineOfSight(this.target))
			return;
		
		entity.swing(InteractionHand.MAIN_HAND);

		if (entity instanceof PossessedSoldierEntity || entity instanceof BaronEntity || entity instanceof FireBaronEntity)
			entity.shootBaron(this.target, damage);

		if (entity instanceof MaykrDroneEntity)
			entity.shootBolt(this.target, damage);

		if (entity instanceof BloodMaykrEntity )
			entity.shootBloodBolt(this.target, damage);

		if (entity instanceof SpiderMastermindEntity || entity instanceof ChaingunnerEntity || entity instanceof ShotgunguyEntity || entity instanceof ZombiemanEntity || entity instanceof MarauderEntity)
			entity.shootChaingun(this.target, entity instanceof ChaingunnerEntity ? DoomConfig.chaingun_bullet_damage : damage);

		if (entity instanceof SpiderMastermind2016Entity || entity instanceof ArachnotronEntity)
			entity.shootEnergyCell(this.target, damage);

		if (entity instanceof MancubusEntity)
			entity.shootMancubus(this.target, damage);

		if (entity instanceof Revenant2016Entity || entity instanceof RevenantEntity || entity instanceof CyberdemonEntity)
			entity.shootRocket(this.target, damage);

		if (entity instanceof HellknightEntity || entity instanceof CacodemonEntity)
			entity.shootFireball(this.target, damage, 0);

		if (entity instanceof ProwlerEntity || entity instanceof MechaZombieEntity || entity instanceof ImpEntity || entity instanceof GargoyleEntity)
			entity.shootSmallFireball(this.target, damage);

		if (entity instanceof MotherDemonEntity) {
			entity.shootFireball(this.target, DoomConfig.motherdemon_ranged_damage + (entity.getEntityData().get(MotherDemonEntity.DEATH_STATE) == 1 ? DoomConfig.motherdemon_phaseone_damage_boos : 0), 0);
			entity.shootFireball(this.target, DoomConfig.motherdemon_ranged_damage + (entity.getEntityData().get(MotherDemonEntity.DEATH_STATE) == 1 ? DoomConfig.motherdemon_phaseone_damage_boos : 0), 3);
			entity.shootFireball(this.target, DoomConfig.motherdemon_ranged_damage + (entity.getEntityData().get(MotherDemonEntity.DEATH_STATE) == 1 ? DoomConfig.motherdemon_phaseone_damage_boos : 0), -3);
		}
		
		if (entity instanceof PossessedScientistEntity)
			entity.throwPotion(this.target);
	}

}
