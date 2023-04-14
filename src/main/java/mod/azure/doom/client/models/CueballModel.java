package mod.azure.doom.client.models;

import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.CueBallEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CueballModel extends GeoModel<CueBallEntity> {

	private static final ResourceLocation[] TEX = { new ResourceLocation(DoomMod.MODID, "textures/entity/cueball.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/cueball_flame_1.png") };

	private static final ResourceLocation[] TEX2 = { new ResourceLocation(DoomMod.MODID, "textures/entity/possessedengineer.png"), new ResourceLocation(DoomMod.MODID, "textures/entity/possessedengineer_1.png") };

	@Override
	public ResourceLocation getModelResource(CueBallEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/" + (object.getVariant() == 3 ? "screecher" : object.getVariant() == 2 ? "possessedengineer" : "cueball") + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CueBallEntity object) {
		return object.getVariant() == 3 ? new ResourceLocation(DoomMod.MODID, "textures/entity/screecher.png") : object.getVariant() == 2 ? TEX2[object.getFlameTimer()] : TEX[object.getFlameTimer()];
	}

	@Override
	public ResourceLocation getAnimationResource(CueBallEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/" + (object.getVariant() == 3 ? "screecher" : "cueengineer") + ".animation.json");
	}

	@Override
	public void setCustomAnimations(CueBallEntity animatable, long instanceId, AnimationState<CueBallEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		final CoreGeoBone left_arm = getAnimationProcessor().getBone("field_191223_g");
		final CoreGeoBone right_arm = getAnimationProcessor().getBone("field_191224_h");
		final CoreGeoBone left_leg = getAnimationProcessor().getBone("field_217143_g");
		final CoreGeoBone right_leg = getAnimationProcessor().getBone("field_217144_h");

		if (left_arm != null)
			left_arm.setRotX(Mth.cos(animatable.walkAnimation.position() * 0.6662F + 3.1415927F) * 2.0F * animatable.walkAnimation.speed() * 0.5F);
		if (right_arm != null)
			right_arm.setRotX(Mth.cos(animatable.walkAnimation.position() * 0.6662F + 3.1415927F) * -2.0F * animatable.walkAnimation.speed() * 0.5F);
		if (left_leg != null)
			left_leg.setRotX(Mth.cos(animatable.walkAnimation.position() * 0.6662F + 3.1415927F) * 2.0F * animatable.walkAnimation.speed() * 0.5F);
		if (right_leg != null)
			right_leg.setRotX(Mth.cos(animatable.walkAnimation.position() * 0.6662F) * 1.4F * animatable.walkAnimation.speed() * 0.5F);
	}

	@Override
	public RenderType getRenderType(CueBallEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}