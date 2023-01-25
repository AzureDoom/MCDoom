package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.RocketMobEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class RocketMobModel extends GeoModel<RocketMobEntity> {
	@Override
	public ResourceLocation getModelResource(RocketMobEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/rocket.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(RocketMobEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/projectiles/rocket.png");
	}

	@Override
	public ResourceLocation getAnimationResource(RocketMobEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/rocket.animation.json");
	}

	@Override
	public RenderType getRenderType(RocketMobEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
