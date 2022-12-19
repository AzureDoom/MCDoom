package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.EnergyCellEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class EnergyModel extends GeoModel<EnergyCellEntity> {
	@Override
	public Identifier getModelResource(EnergyCellEntity object) {
		return new Identifier(DoomMod.MODID, "geo/smallprojectile.geo.json");
	}

	@Override
	public Identifier getTextureResource(EnergyCellEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/projectiles/plasma_ball.png");
	}

	@Override
	public Identifier getAnimationResource(EnergyCellEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/smallprojectile.animation.json");
	}

	@Override
	public RenderLayer getRenderType(EnergyCellEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
