package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.BarenBlastEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BarenBlastModel extends AnimatedGeoModel<BarenBlastEntity> {
	@Override
	public ResourceLocation getModelResource(BarenBlastEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/smallprojectile.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BarenBlastEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/projectiles/plasma_ball_red.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BarenBlastEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/smallprojectile.animation.json");
	}
}
