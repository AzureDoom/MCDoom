package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.WhiplashEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class WhiplashModel extends GeoModel<WhiplashEntity> {

	@Override
	public ResourceLocation getModelResource(WhiplashEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/whiplash.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WhiplashEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/whiplash.png");
	}

	@Override
	public ResourceLocation getAnimationResource(WhiplashEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/whiplash.animation.json");
	}

	@Override
	public RenderType getRenderType(WhiplashEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}