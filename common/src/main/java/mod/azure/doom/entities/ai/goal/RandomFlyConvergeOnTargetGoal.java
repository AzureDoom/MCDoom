package mod.azure.doom.entities.ai.goal;

import mod.azure.doom.entities.DemonEntity;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class RandomFlyConvergeOnTargetGoal extends Goal {
    private final DemonEntity parentEntity;

    /**
     * @param entity Entity that is flying
     */
    public RandomFlyConvergeOnTargetGoal(DemonEntity entity) {
        this.parentEntity = entity;
        this.setFlags(EnumSet.of(Flag.MOVE));
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

    @Override
    public boolean canContinueToUse() {
        return false;
    }

    public boolean shouldConverge(LivingEntity target) {
        return target != null && this.parentEntity.distanceToSqr(target) >= 225;
    }

    @Override
    public void start() {
        LivingEntity target = this.parentEntity.getTarget();
        boolean converge = shouldConverge(target);
        RandomSource random = this.parentEntity.getRandom();
        double d0 = this.parentEntity.getX() + ((random.nextFloat() * 2.0F - 1.0F) * 2.0F);
        double d1 = this.parentEntity.getY() + ((random.nextFloat() * 2.0F - 1.0F) * 2.0F);
        double d2 = this.parentEntity.getZ() + ((random.nextFloat() * 2.0F - 1.0F) * 2.0F);

        if (converge) {
            assert target != null;
            double xDifference = target.getX() - this.parentEntity.getX();
            double yDifference = target.getY() - this.parentEntity.getY();
            double zDifference = target.getZ() - this.parentEntity.getZ();
            double maxAbs = Math.max(Math.abs(xDifference), Math.abs(yDifference));
            maxAbs = Math.max(maxAbs, Math.abs(zDifference));
            d0 += 2 * xDifference / maxAbs * 0.5;
            d1 += 2 * yDifference / maxAbs * 0.5;
            d2 += 2 * zDifference / maxAbs * 0.5;
        }
        this.parentEntity.getMoveControl().setWantedPosition(d0, d1, d2, 2);
    }
}