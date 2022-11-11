package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import org.jetbrains.annotations.Nullable;

import mod.azure.doom.entity.tierambient.CueBallEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

public class IgniteDemonGoal extends Goal {
	private final CueBallEntity creeper;
	@Nullable
	private LivingEntity target;

	public IgniteDemonGoal(CueBallEntity creeper) {
		this.creeper = creeper;
		this.setControls(EnumSet.of(Goal.Control.MOVE));
	}

	@Override
	public boolean canStart() {
		LivingEntity livingEntity = this.creeper.getTarget();
		return this.creeper.getFuseSpeed() > 0
				|| livingEntity != null && this.creeper.squaredDistanceTo(livingEntity) < 9.0;
	}

	@Override
	public void start() {
		this.creeper.getNavigation().stop();
		this.target = this.creeper.getTarget();
	}

	@Override
	public void stop() {
		this.target = null;
	}

	@Override
	public boolean shouldRunEveryTick() {
		return true;
	}

	@Override
	public void tick() {
		if (this.target == null) {
			this.creeper.setFuseSpeed(-1);
			return;
		}
		if (this.creeper.squaredDistanceTo(this.target) > 49.0) {
			this.creeper.setFuseSpeed(-1);
			return;
		}
		if (!this.creeper.getVisibilityCache().canSee(this.target)) {
			this.creeper.setFuseSpeed(-1);
			return;
		}
		this.creeper.setFuseSpeed(1);
	}
}