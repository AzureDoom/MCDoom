package mod.azure.doom.client.models;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.WhiplashEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class WhiplashModel extends GeoModel<WhiplashEntity> {

	@Override
	public ResourceLocation getModelResource(WhiplashEntity object) {
		return DoomMod.modResource("geo/whiplash.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WhiplashEntity object) {
		return DoomMod.modResource("textures/entity/whiplash.png");
	}

	@Override
	public ResourceLocation getAnimationResource(WhiplashEntity object) {
		return DoomMod.modResource("animations/whiplash.animation.json");
	}

	@Override
	public RenderType getRenderType(WhiplashEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}