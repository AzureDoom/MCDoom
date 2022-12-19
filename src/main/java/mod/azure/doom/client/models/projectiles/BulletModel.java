package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.BulletEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BulletModel extends GeoModel<BulletEntity> {
	@Override
	public Identifier getModelResource(BulletEntity object) {
		return new Identifier(DoomMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public Identifier getTextureResource(BulletEntity object) {
		return new Identifier(DoomMod.MODID, "textures/item/clip.png");
	}

	@Override
	public Identifier getAnimationResource(BulletEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/empty.animation.json");
	}

	@Override
	public RenderLayer getRenderType(BulletEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
