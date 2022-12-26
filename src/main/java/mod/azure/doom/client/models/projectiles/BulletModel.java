package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.BulletEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BulletModel extends GeoModel<BulletEntity> {
	@Override
	public ResourceLocation getModelResource(BulletEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BulletEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/clip.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BulletEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/empty.animation.json");
	}

	@Override
	public RenderType getRenderType(BulletEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
