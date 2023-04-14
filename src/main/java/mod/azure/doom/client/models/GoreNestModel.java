package mod.azure.doom.client.models;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.GoreNestEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class GoreNestModel extends GeoModel<GoreNestEntity> {

	@Override
	public ResourceLocation getModelResource(GoreNestEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/gorenest.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GoreNestEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/gore_nest.png");
	}

	@Override
	public ResourceLocation getAnimationResource(GoreNestEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/gorenest_animation.json");
	}

	@Override
	public RenderType getRenderType(GoreNestEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}