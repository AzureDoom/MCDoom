package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MotherDemonModel extends AnimatedGeoModel<MotherDemonEntity> {
	
	@Override
	public ResourceLocation getModelLocation(MotherDemonEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/motherdemon.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(MotherDemonEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/motherdemon.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(MotherDemonEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/motherdemon.animation.json");
	}
}