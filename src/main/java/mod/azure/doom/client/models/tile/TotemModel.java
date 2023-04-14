package mod.azure.doom.client.models.tile;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tileentity.TotemEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class TotemModel extends GeoModel<TotemEntity> {
	@Override
	public ResourceLocation getAnimationResource(TotemEntity entity) {
		return new ResourceLocation(DoomMod.MODID, "animations/totem.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TotemEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "geo/totem.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TotemEntity entity) {
		return new ResourceLocation(DoomMod.MODID, "textures/block/totem.png");
	}

	@Override
	public RenderType getRenderType(TotemEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
