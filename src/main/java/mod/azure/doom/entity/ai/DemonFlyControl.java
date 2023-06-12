package mod.azure.doom.entity.ai;

import mod.azure.doom.entity.DemonEntity;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class DemonFlyControl extends MoveControl {
	protected final DemonEntity entity;
	private int courseChangeCooldown;

	public DemonFlyControl(DemonEntity entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public void tick() {
		if (entity.isAggressive()) {
			if (operation == MoveControl.Operation.MOVE_TO) {
				if (courseChangeCooldown-- <= 0) {
					courseChangeCooldown += entity.getRandom().nextInt(5) + 2;
					var vector3d = new Vec3(wantedX - entity.getX(), wantedY - entity.getY(), wantedZ - entity.getZ());
					final var d0 = vector3d.length();
					vector3d = vector3d.normalize();
					if (canReach(vector3d, Mth.ceil(d0)))
						entity.setDeltaMovement(entity.getDeltaMovement().add(vector3d.scale(0.1D)));
					else
						operation = MoveControl.Operation.WAIT;
				}
			} else {
				operation = MoveControl.Operation.WAIT;
				entity.setZza(0.0F);
			}
		} else if (operation == MoveControl.Operation.MOVE_TO) {
			operation = MoveControl.Operation.WAIT;
			final var d0 = wantedX - entity.getX();
			final var d1 = wantedZ - entity.getZ();
			final var d2 = wantedY - entity.getY();
			final var d3 = d0 * d0 + d2 * d2 + d1 * d1;
			if (d3 < 2.5000003E-7F) {
				entity.setZza(0.0F);
				return;
			}
			final var f9 = (float) (Mth.atan2(d1, d0) * (180F / (float) Math.PI)) - 90.0F;
			entity.setYRot(rotlerp(mob.getYRot(), f9, 90.0F));
			entity.setSpeed((float) 0.25D);
			final var blockpos = mob.blockPosition();
			final var blockstate = mob.level().getBlockState(blockpos);
			final var voxelshape = blockstate.getCollisionShape(mob.level(), blockpos);
			if (d2 > mob.getEyeHeight() && d0 * d0 + d1 * d1 < Math.max(1.0F, mob.getBbWidth()) || !voxelshape.isEmpty() && mob.getY() < voxelshape.max(Direction.Axis.Y) + blockpos.getY() && !blockstate.is(BlockTags.DOORS) && !blockstate.is(BlockTags.FENCES))
				operation = MoveControl.Operation.JUMPING;
		} else if (operation == MoveControl.Operation.JUMPING) {
			mob.setSpeed((float) 0.25D);
			if (mob.onGround())
				operation = MoveControl.Operation.WAIT;
		} else {
			operation = MoveControl.Operation.WAIT;
			entity.setZza(0.0F);
		}
	}

	private boolean canReach(Vec3 direction, int steps) {
		var axisalignedbb = mob.getBoundingBox();
		for (var i = 1; i < steps; ++i) {
			axisalignedbb = axisalignedbb.move(direction);
			if (!mob.level().noCollision(entity, axisalignedbb))
				return false;
		}
		return true;
	}
}