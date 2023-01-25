package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class SpiderMastermindModel extends GeoModel<SpiderMastermindEntity> {

	@Override
	public ResourceLocation getModelResource(SpiderMastermindEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/spidermastermind.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SpiderMastermindEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/spidermastermind-texturemap.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SpiderMastermindEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/spidermastermind_animation.json");
	}

	@Override
	public RenderType getRenderType(SpiderMastermindEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}