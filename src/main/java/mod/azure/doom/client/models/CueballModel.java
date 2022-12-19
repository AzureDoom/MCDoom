package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.CueBallEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class CueballModel extends GeoModel<CueBallEntity> {

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
	public void setCustomAnimations(CueBallEntity animatable, long instanceId,
			AnimationState<CueBallEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone left_arm = getAnimationProcessor().getBone("field_191223_g");
		CoreGeoBone right_arm = getAnimationProcessor().getBone("field_191224_h");
		CoreGeoBone left_leg = getAnimationProcessor().getBone("field_217143_g");
		CoreGeoBone right_leg = getAnimationProcessor().getBone("field_217144_h");

		if (left_arm != null) {
			left_arm.setRotX(MathHelper.cos(animatable.limbAngle * 0.6662F + 3.1415927F) * 2.0F
					* animatable.limbDistance * 0.5F);
		}
		if (right_arm != null) {
			right_arm.setRotX(MathHelper.cos(animatable.limbAngle * 0.6662F + 3.1415927F) * -2.0F
					* animatable.limbDistance * 0.5F);
		}
		if (left_leg != null) {
			left_leg.setRotX(MathHelper.cos(animatable.limbAngle * 0.6662F + 3.1415927F) * 2.0F
					* animatable.limbDistance * 0.5F);
		}
		if (right_leg != null) {
			right_leg.setRotX(MathHelper.cos(animatable.limbAngle * 0.6662F) * 1.4F * animatable.limbDistance * 0.5F);
		}
	}

	@Override
	public RenderLayer getRenderType(CueBallEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

}