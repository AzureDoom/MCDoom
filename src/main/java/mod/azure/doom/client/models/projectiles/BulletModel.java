package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.BulletEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BulletModel extends AnimatedGeoModel<BulletEntity> {
	@Override
	public ResourceLocation getModelResource(BulletEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BulletEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/clip.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BulletEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/empty.animation.json");
	}
}
