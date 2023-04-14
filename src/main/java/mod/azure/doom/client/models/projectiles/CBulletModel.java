package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.ChaingunBulletEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class CBulletModel extends GeoModel<ChaingunBulletEntity> {
	@Override
	public ResourceLocation getModelResource(ChaingunBulletEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ChaingunBulletEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/clip.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ChaingunBulletEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/empty.animation.json");
	}

	@Override
	public RenderType getRenderType(ChaingunBulletEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
