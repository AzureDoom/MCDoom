package mod.azure.doom.client.models;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.ArachnotronEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ArachonotronEternalModel extends GeoModel<ArachnotronEntity> {

	@Override
	public ResourceLocation getModelResource(ArachnotronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/arachonotroneternal.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ArachnotronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/arachonotroneternal.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ArachnotronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/arachonotroneternal.animation.json");
	}

	@Override
	public RenderType getRenderType(ArachnotronEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}