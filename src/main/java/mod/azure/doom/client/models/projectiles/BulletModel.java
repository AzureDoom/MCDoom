package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.BulletEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class BulletModel extends GeoModel<BulletEntity> {
	@Override
	public ResourceLocation getModelResource(BulletEntity object) {
		return DoomMod.modResource("geo/bullet.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BulletEntity object) {
		return DoomMod.modResource("textures/item/clip.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BulletEntity animatable) {
		return DoomMod.modResource("animations/empty.animation.json");
	}

	@Override
	public RenderType getRenderType(BulletEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
