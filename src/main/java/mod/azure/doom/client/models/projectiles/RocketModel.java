package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.RocketEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class RocketModel extends GeoModel<RocketEntity> {
	@Override
	public ResourceLocation getModelResource(RocketEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/rocket.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RocketEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/projectiles/rocket.png");
	}

	@Override
	public ResourceLocation getAnimationResource(RocketEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/rocket.animation.json");
	}

	@Override
	public RenderType getRenderType(RocketEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
