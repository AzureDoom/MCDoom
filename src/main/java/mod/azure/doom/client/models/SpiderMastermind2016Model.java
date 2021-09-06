package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.SpiderMastermind2016Entity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpiderMastermind2016Model extends AnimatedGeoModel<SpiderMastermind2016Entity> {

	@Override
	public ResourceLocation getModelLocation(SpiderMastermind2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/spidermastermind2016.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SpiderMastermind2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/spidermastermind2016.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(SpiderMastermind2016Entity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/spidermastermind2016.animation.json");
	}
}