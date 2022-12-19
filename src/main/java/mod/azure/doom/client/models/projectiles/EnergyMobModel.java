package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.EnergyCellMobEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class EnergyMobModel extends GeoModel<EnergyCellMobEntity> {
	@Override
	public Identifier getModelResource(EnergyCellMobEntity object) {
		return new Identifier(DoomMod.MODID, "geo/smallprojectile.geo.json");
	}

	@Override
	public Identifier getTextureResource(EnergyCellMobEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/projectiles/plasma_ball.png");
	}

	@Override
	public Identifier getAnimationResource(EnergyCellMobEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/smallprojectile.animation.json");
	}

	@Override
	public RenderLayer getRenderType(EnergyCellMobEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
