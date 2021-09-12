package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.BloodBoltEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BloodBoltModel extends AnimatedGeoModel<BloodBoltEntity> {
	@Override
	public ResourceLocation getModelLocation(BloodBoltEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/bloodbolt.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(BloodBoltEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/projectiles/bloodbolt.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(BloodBoltEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/smallprojectile.animation.json");
	}
}
