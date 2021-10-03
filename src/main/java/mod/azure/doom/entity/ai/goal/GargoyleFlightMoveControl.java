package mod.azure.doom.entity.ai.goal;

import mod.azure.doom.entity.tierfodder.GargoyleEntity;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;

public class GargoyleFlightMoveControl extends MoveControl {
	private final int maxPitchChange;
	private final boolean noGravity;
	protected final GargoyleEntity entity;

	public GargoyleFlightMoveControl(GargoyleEntity entity, int maxPitchChange, boolean noGravity) {
		super(entity);
		this.maxPitchChange = maxPitchChange;
		this.noGravity = noGravity;
		this.entity = entity;
	}

	public void tick() {
		if (this.operation == MoveControl.Operation.MOVE_TO) {
			this.operation = MoveControl.Operation.WAIT;
			this.entity.setNoGravity(true);
			double d = this.wantedX - this.entity.getX();
			double e = this.wantedY - this.entity.getY();
			double f = this.wantedZ - this.entity.getZ();
			double g = d * d + e * e + f * f;
			if (g < 2.500000277905201E-7D) {
				this.mob.setYya(0.0F);
				this.mob.setZza(0.0F);
				return;
			}

			float h = (float) (Mth.atan2(f, d) * 57.2957763671875D) - 90.0F;
			this.mob.setYRot(this.rotlerp(this.mob.getYRot(), h, 90.0F));
			float j;
			if (this.entity.isOnGround()) {
				j = (float) (this.speedModifier * this.entity.getAttributeValue(Attributes.MOVEMENT_SPEED));
			} else {
				j = (float) (this.speedModifier * this.entity.getAttributeValue(Attributes.FLYING_SPEED));
			}

			this.entity.setSpeed(j * 2);
			double k = Math.sqrt(d * d + f * f);
			if (Math.abs(e) > 9.999999747378752E-6D || Math.abs(k) > 9.999999747378752E-6D) {
				float l = (float) (-(Mth.atan2(e, k) * 57.2957763671875D));
				this.entity.setXRot(this.rotlerp(this.entity.getXRot(), l, (float) this.maxPitchChange));
				this.entity.setYya(e > 0.0D ? j * 2 : -j * 2);
			}
		} else {
			if (!this.noGravity) {
				this.entity.setNoGravity(false);
			}
			this.mob.setYya(0.0F);
			this.mob.setZza(0.0F);
		}

	}
}
