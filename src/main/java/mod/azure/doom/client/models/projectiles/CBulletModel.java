package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.ChaingunBulletEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CBulletModel extends AnimatedGeoModel<ChaingunBulletEntity> {
	@Override
	public ResourceLocation getModelResource(ChaingunBulletEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ChaingunBulletEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/clip.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ChaingunBulletEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/empty.animation.json");
	}
}
