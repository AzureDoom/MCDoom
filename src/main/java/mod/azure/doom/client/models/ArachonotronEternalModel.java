package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.ArachnotronEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ArachonotronEternalModel extends AnimatedGeoModel<ArachnotronEntity> {

	@Override
	public ResourceLocation getModelLocation(ArachnotronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/arachonotroneternal.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ArachnotronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/arachonotroneternal.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ArachnotronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/arachonotroneternal.animation.json");
	}

}