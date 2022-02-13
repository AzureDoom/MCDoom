package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.GladiatorMaceEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GladiatorMaceModel extends AnimatedGeoModel<GladiatorMaceEntity> {
	@Override
	public ResourceLocation getModelLocation(GladiatorMaceEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/gladiator_mace.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(GladiatorMaceEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/gladiator.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(GladiatorMaceEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/gladiator_mace.animation.json");
	}
}
