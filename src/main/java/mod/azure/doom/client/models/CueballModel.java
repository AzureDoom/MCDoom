package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.CueBallEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3q.model.AnimatedTickingGeoModel;

public class CueballModel extends AnimatedTickingGeoModel<CueBallEntity> {

	private static final Identifier[] TEX = { new Identifier(DoomMod.MODID, "textures/entity/cueball.png"),
			new Identifier(DoomMod.MODID, "textures/entity/cueball_flame_1.png") };

	private static final Identifier[] TEX2 = { new Identifier(DoomMod.MODID, "textures/entity/possessedengineer.png"),
			new Identifier(DoomMod.MODID, "textures/entity/possessedengineer_1.png") };

	@Override
	public Identifier getModelResource(CueBallEntity object) {
		return new Identifier(DoomMod.MODID, "geo/"
				+ (object.getVariant() == 3 ? "screecher" : object.getVariant() == 2 ? "possessedengineer" : "cueball")
				+ ".geo.json");
	}

	@Override
	public Identifier getTextureResource(CueBallEntity object) {
		return object.getVariant() == 3 ? new Identifier(DoomMod.MODID, "textures/entity/screecher.png")
				: object.getVariant() == 2 ? TEX2[(object.getFlameTimer())] : TEX[(object.getFlameTimer())];
	}

	@Override
	public Identifier getAnimationResource(CueBallEntity object) {
		return new Identifier(DoomMod.MODID,
				"animations/" + (object.getVariant() == 3 ? "screecher" : "cueengineer") + ".animation.json");
	}

	@Override
	public void setLivingAnimations(CueBallEntity animatable, Integer instanceId, AnimationEvent animationEvent) {
		super.setLivingAnimations(animatable, instanceId, animationEvent);

		IBone Left_arm = this.getAnimationProcessor().getBone("field_191223_g");
		IBone Right_arm = this.getAnimationProcessor().getBone("field_191224_h");
		IBone Left_leg = this.getAnimationProcessor().getBone("field_217143_g");
		IBone Right_leg = this.getAnimationProcessor().getBone("field_217144_h");

		if (Left_arm != null && animatable.getVariant() != 3) {
			Left_arm.setRotationX(Vec3f.POSITIVE_X
					.getRadialQuaternion(
							MathHelper.cos(animatable.limbAngle * 0.6662F) * 2.0F * animatable.limbDistance * 0.5F)
					.getX());
		}
		if (Right_arm != null && animatable.getVariant() != 3) {
			Right_arm.setRotationX(Vec3f.POSITIVE_X.getRadialQuaternion(
					MathHelper.cos(animatable.limbAngle * 0.6662F + 3.1415927F) * 2.0F * animatable.limbDistance * 0.5F)
					.getX());
		}
		if (Left_leg != null && animatable.getVariant() != 3) {
			Left_leg.setRotationX(Vec3f.POSITIVE_X.getRadialQuaternion(
					MathHelper.cos(animatable.limbAngle * 0.6662F + 3.1415927F) * 1.4F * animatable.limbDistance * 0.5F)
					.getX());
		}
		if (Right_leg != null && animatable.getVariant() != 3) {
			Right_leg.setRotationX(Vec3f.POSITIVE_X
					.getRadialQuaternion(
							MathHelper.cos(animatable.limbAngle * 0.6662F) * 1.4F * animatable.limbDistance * 0.5F)
					.getX());
		}
	}

}