package mod.azure.doom.client.models;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class SpiderMastermindModel extends GeoModel<SpiderMastermindEntity> {

	@Override
	public ResourceLocation getModelResource(SpiderMastermindEntity object) {
		return DoomMod.modResource("geo/spidermastermind.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SpiderMastermindEntity object) {
		return DoomMod.modResource("textures/entity/spidermastermind-texturemap.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SpiderMastermindEntity object) {
		return DoomMod.modResource("animations/spidermastermind_animation.json");
	}

	@Override
	public RenderType getRenderType(SpiderMastermindEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}