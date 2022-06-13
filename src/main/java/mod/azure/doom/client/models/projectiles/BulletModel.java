package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.BulletEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BulletModel extends AnimatedGeoModel<BulletEntity> {
	@Override
	public Identifier getModelResource(BulletEntity object) {
		return new Identifier(DoomMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public Identifier getTextureResource(BulletEntity object) {
		return new Identifier(DoomMod.MODID, "textures/items/clip.png");
	}

	@Override
	public Identifier getAnimationResource(BulletEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/empty.animation.json");
	}
}
