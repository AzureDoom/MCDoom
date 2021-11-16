package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class SpiderMastermindModel extends AnimatedTickingGeoModel<SpiderMastermindEntity> {

	@Override
	public ResourceLocation getModelLocation(SpiderMastermindEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/spidermastermind.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SpiderMastermindEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/spidermastermind-texturemap.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(SpiderMastermindEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/spidermastermind_animation.json");
	}
}