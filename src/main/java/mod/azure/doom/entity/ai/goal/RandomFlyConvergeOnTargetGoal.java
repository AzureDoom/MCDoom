package mod.azure.doom.entity.ai.goal;

import java.util.EnumSet;

import mod.azure.doom.entity.DemonEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;

public class RandomFlyConvergeOnTargetGoal extends Goal {
	private final DemonEntity parentEntity;
	private double flySpeed = 1;
	private double convergeDistance = 200;
	private double convergenceAdherence = 0.25;

	/**
	 * 
	 * @param entity
	 * @param flySpeed             The flying speed of the entity.
	 * @param convergeDistance     The distance in blocks from the target at which
	 *                             the entity starts to trend towards moving back
	 *                             towards it.
	 * @param convergenceAdherence The strength of the convergence. Max value is 1,
	 *                             default 0.25.
	 */
	public RandomFlyConvergeOnTargetGoal(DemonEntity entity, double flySpeed, double convergeDistance,
			double convergenceAdherence) {
		this.parentEntity = entity;
		this.flySpeed = flySpeed;
		this.convergeDistance = convergeDistance * convergeDistance;
		this.convergenceAdherence = convergenceAdherence;
		this.setFlags(EnumSet.of(Goal.Flag.MOVE));
	}

	public boolean canUse() {
		MoveControl movementcontroller = this.parentEntity.getMoveControl();
		if (!movementcontroller.hasWanted()) {
			return true;
		} else {
			double d0 = movementcontroller.getWantedX() - this.parentEntity.getX();
			double d1 = movementcontroller.getWantedY() - this.parentEntity.getY();
			double d2 = movementcontroller.getWantedZ() - this.parentEntity.getZ();
			double d3 = d0 * d0 + d1 * d1 + d2 * d2;
			return d3 < 1.0D || d3 > 10.0D;
		}
	}

	public boolean canContinueToUse() {
		return false;
	}

	public boolean shouldConverge(LivingEntity target) {
		return target != null && this.parentEntity.distanceToSqr(target) >= convergeDistance;
	}

	public void start() {
		LivingEntity target = this.parentEntity.getTarget();
		boolean converge = shouldConverge(target);
		RandomSource random = this.parentEntity.getRandom();
		double d0 = this.parentEntity.getX() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 2.0F);
		double d1 = this.parentEntity.getY() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 2.0F);
		double d2 = this.parentEntity.getZ() + (double) ((random.nextFloat() * 2.0F - 1.0F) * 2.0F);

		if (converge) {
			double xDifference = target.getX() - this.parentEntity.getX();
			double yDifference = target.getY() - this.parentEntity.getY();
			double zDifference = target.getZ() - this.parentEntity.getZ();
			double maxAbs = Math.max(Math.abs(xDifference), Math.abs(yDifference));
			maxAbs = Math.max(maxAbs, Math.abs(zDifference));
			d0 += 2 * xDifference / maxAbs * convergenceAdherence;
			d1 += 2 * yDifference / maxAbs * convergenceAdherence;
			d2 += 2 * zDifference / maxAbs * convergenceAdherence;
		}
		this.parentEntity.getMoveControl().setWantedPosition(d0, d1, d2, flySpeed);
	}
}