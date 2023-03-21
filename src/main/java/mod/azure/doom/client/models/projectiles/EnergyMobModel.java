package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.EnergyCellMobEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class EnergyMobModel extends GeoModel<EnergyCellMobEntity> {
	@Override
	public ResourceLocation getModelResource(EnergyCellMobEntity object) {
		return DoomMod.modResource("geo/smallprojectile.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(EnergyCellMobEntity object) {
		return DoomMod.modResource("textures/entity/projectiles/plasma_ball.png");
	}

	@Override
	public ResourceLocation getAnimationResource(EnergyCellMobEntity animatable) {
		return DoomMod.modResource("animations/smallprojectile.animation.json");
	}

	@Override
	public RenderType getRenderType(EnergyCellMobEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
