package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.MaykrDroneEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class MaykrDroneModel extends GeoModel<MaykrDroneEntity> {

	@Override
	public ResourceLocation getModelResource(MaykrDroneEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/maykrdrone.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MaykrDroneEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/maykrdrone_" + object.getVariant() + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(MaykrDroneEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/maykrdrone.animation.json");
	}

	@Override
	public RenderType getRenderType(MaykrDroneEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
