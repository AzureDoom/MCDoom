package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.ChaingunBulletEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CBulletModel extends GeoModel<ChaingunBulletEntity> {
	@Override
	public Identifier getModelResource(ChaingunBulletEntity object) {
		return new Identifier(DoomMod.MODID, "geo/bullet.geo.json");
	}

	@Override
	public Identifier getTextureResource(ChaingunBulletEntity object) {
		return new Identifier(DoomMod.MODID, "textures/item/clip.png");
	}

	@Override
	public Identifier getAnimationResource(ChaingunBulletEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/empty.animation.json");
	}

	@Override
	public RenderLayer getRenderType(ChaingunBulletEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
