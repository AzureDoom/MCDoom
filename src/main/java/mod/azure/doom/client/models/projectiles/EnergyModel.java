package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.EnergyCellEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EnergyModel extends AnimatedGeoModel<EnergyCellEntity> {
	@Override
	public ResourceLocation getModelResource(EnergyCellEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/smallprojectile.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(EnergyCellEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/projectiles/plasma_ball.png");
	}

	@Override
	public ResourceLocation getAnimationResource(EnergyCellEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/smallprojectile.animation.json");
	}
}
