package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.GrenadeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GrenadeModel extends AnimatedGeoModel<GrenadeEntity> {
	@Override
	public ResourceLocation getModelLocation(GrenadeEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomed_grenade.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(GrenadeEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/doomed_grenade.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(GrenadeEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/doomed_grenade.animation.json");
	}
}
