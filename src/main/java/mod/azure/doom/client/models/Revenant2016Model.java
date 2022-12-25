package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.Revenant2016Entity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class Revenant2016Model extends GeoModel<Revenant2016Entity> {

	private static final ResourceLocation[] TEX = { new ResourceLocation(DoomMod.MODID, "textures/entity/revenant.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_1.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_2.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_3.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_4.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_5.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_6.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_7.png") };

	private static final ResourceLocation[] TEX2 = { new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden_1.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden_2.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden_3.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden_4.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden_5.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden_6.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant_golden_7.png") };

	private static final ResourceLocation[] TEX3 = { new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016_1.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016_2.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016_3.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016_4.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016_5.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016_6.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/revenant2016_7.png") };

	@Override
	public ResourceLocation getModelResource(Revenant2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/revenant.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Revenant2016Entity object) {
		return (object.getVariant() == 11 ? TEX2[(object.getFlameTimer())]
				: object.getVariant() == 2 ? TEX3[(object.getFlameTimer())] : TEX[(object.getFlameTimer())]);
	}

	@Override
	public ResourceLocation getAnimationResource(Revenant2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/revenant.animation.json");
	}

	@Override
	public void setCustomAnimations(Revenant2016Entity animatable, long instanceId,
			AnimationState<Revenant2016Entity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("head");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}

	@Override
	public RenderType getRenderType(Revenant2016Entity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}