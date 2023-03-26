package mod.azure.doom.entity.task;

import java.util.List;
import java.util.function.Predicate;

import com.mojang.datafixers.util.Pair;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import mod.azure.doom.entity.DemonEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.tslat.smartbrainlib.api.core.behaviour.ExtendedBehaviour;
import net.tslat.smartbrainlib.util.BrainUtils;

public class StrafeScreamTarget<E extends DemonEntity> extends ExtendedBehaviour<E> {
	private static final List<Pair<MemoryModuleType<?>, MemoryStatus>> MEMORY_REQUIREMENTS = ObjectArrayList.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT), Pair.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));

	protected boolean strafingLaterally = false;
	protected boolean strafingBack = false;
	protected int strafeCounter = -1;

	protected float strafeDistanceSqr = 244;
	protected Predicate<E> stopStrafingWhen = entity -> false;
	protected float speedMod = 1;

	@Override
	protected List<Pair<MemoryModuleType<?>, MemoryStatus>> getMemoryRequirements() {
		return MEMORY_REQUIREMENTS;
	}

	public StrafeScreamTarget<E> stopStrafingWhen(Predicate<E> predicate) {
		this.stopStrafingWhen = predicate;

		return this;
	}

	public StrafeScreamTarget<E> strafeDistance(float distance) {
		this.strafeDistanceSqr = distance * distance;

		return this;
	}

	public StrafeScreamTarget<E> speedMod(float modifier) {
		this.speedMod = modifier;

		return this;
	}

	@Override
	protected boolean shouldKeepRunning(E entity) {
		return BrainUtils.hasMemory(entity, MemoryModuleType.ATTACK_TARGET) && !this.stopStrafingWhen.test(entity);
	}

	@Override
	protected void tick(E entity) {
		LivingEntity target = BrainUtils.getTargetOfEntity(entity);
		double distanceToTarget = target.distanceToSqr(entity);

		if (distanceToTarget <= this.strafeDistanceSqr) {
			entity.getNavigation().stop();
			entity.setScreamingStatus(true);
			this.strafeCounter++;
		} else {
			entity.getNavigation().moveTo(target, this.speedMod);
			this.strafeCounter = -1;
		}

		if (this.strafeCounter >= 20) {
			if (entity.getRandom().nextFloat() < 0.3)
				this.strafingLaterally = !this.strafingLaterally;

			if (entity.getRandom().nextFloat() < 0.3)
				this.strafingBack = !this.strafingBack;

			this.strafeCounter = 0;
		}

		if (this.strafeCounter > -1) {
			if (distanceToTarget > this.strafeDistanceSqr * 0.75f) {
				this.strafingBack = false;
			} else if (distanceToTarget < this.strafeDistanceSqr * 0.25f) {
				this.strafingBack = true;
			}

			entity.lookAt(target, 30, 30);
			entity.getMoveControl().strafe(this.strafingBack ? -0.5f : 0.5f, this.strafingLaterally ? 0.5f : -0.5f);
		}
	}
}