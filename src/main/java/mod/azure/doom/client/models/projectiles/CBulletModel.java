package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.ChaingunBulletEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CBulletModel extends AnimatedGeoModel<ChaingunBulletEntity> {
	@Override
	public Identifier getModelResource(ChaingunBulletEntity object) {
		return new Identifier(DoomMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public Identifier getTextureResource(ChaingunBulletEntity object) {
		return new Identifier(DoomMod.MODID, "textures/items/clip.png");
	}

	@Override
	public Identifier getAnimationResource(ChaingunBulletEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/empty.animation.json");
	}
}
