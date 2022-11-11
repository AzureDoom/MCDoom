package mod.azure.doom.client.models;

import com.mojang.math.Vector3f;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.CueBallEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class CueballModel extends AnimatedTickingGeoModel<CueBallEntity> {

	private static final ResourceLocation[] TEX = { new ResourceLocation(DoomMod.MODID, "textures/entity/cueball.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/cueball_flame_1.png") };

	private static final ResourceLocation[] TEX2 = { new ResourceLocation(DoomMod.MODID, "textures/entity/possessedengineer.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/possessedengineer_1.png") };

	@Override
	public ResourceLocation getModelResource(CueBallEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/"
				+ (object.getVariant() == 3 ? "screecher" : object.getVariant() == 2 ? "possessedengineer" : "cueball")
				+ ".geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CueBallEntity object) {
		return object.getVariant() == 3 ? new ResourceLocation(DoomMod.MODID, "textures/entity/screecher.png")
				: object.getVariant() == 2 ? TEX2[(object.getFlameTimer())] : TEX[(object.getFlameTimer())];
	}

	@Override
	public ResourceLocation getAnimationResource(CueBallEntity object) {
		return new ResourceLocation(DoomMod.MODID,
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
			Left_arm.setRotationX(Vector3f.XP
					.rotation(Mth.cos(animatable.animationPosition * 0.6662F) * 2.0F * animatable.animationSpeed * 0.5F)
					.i());
		}
		if (Right_arm != null && animatable.getVariant() != 3) {
			Right_arm.setRotationX(Vector3f.XP.rotation(Mth.cos(animatable.animationPosition * 0.6662F + 3.1415927F)
					* 2.0F * animatable.animationSpeed * 0.5F).i());
		}
		if (Left_leg != null && animatable.getVariant() != 3) {
			Left_leg.setRotationX(Vector3f.XP.rotation(Mth.cos(animatable.animationPosition * 0.6662F + 3.1415927F)
					* 1.4F * animatable.animationSpeed * 0.5F).i());
		}
		if (Right_leg != null && animatable.getVariant() != 3) {
			Right_leg.setRotationX(Vector3f.XP
					.rotation(Mth.cos(animatable.animationPosition * 0.6662F) * 1.4F * animatable.animationSpeed * 0.5F)
					.i());
		}
	}

}