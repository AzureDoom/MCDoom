package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class ArchvileFiringModel extends GeoModel<DoomFireEntity> {
	@Override
	public ResourceLocation getModelResource(DoomFireEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/archvilefiring.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DoomFireEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/empty.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DoomFireEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/archvilefiring.animation.json");
	}

	@Override
	public RenderType getRenderType(DoomFireEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
