package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.DoomHunterEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class DoomHunterModel extends GeoModel<DoomHunterEntity> {

	private static final ResourceLocation[] TEX = { new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter_1.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter_2.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter_3.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter_4.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter_5.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter_6.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/doomhunter_7.png") };

	@Override
	public ResourceLocation getModelResource(DoomHunterEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomhunter.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DoomHunterEntity object) {
		return TEX[(object.getFlameTimer())];
	}

	@Override
	public ResourceLocation getAnimationResource(DoomHunterEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/doomhunter.animation.json");
	}

	@Override
	public void setCustomAnimations(DoomHunterEntity animatable, long instanceId,
			AnimationState<DoomHunterEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);

		CoreGeoBone head = getAnimationProcessor().getBone("neck");
		EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		if (head != null) {
			head.setRotX(entityData.headPitch() * ((float) Math.PI / 270F));
			head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 270F));
		}
	}

	@Override
	public RenderType getRenderType(DoomHunterEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}