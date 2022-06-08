package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class SpiderMastermindModel extends AnimatedTickingGeoModel<SpiderMastermindEntity> {

	@Override
	public ResourceLocation getModelResource(SpiderMastermindEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/spidermastermind.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SpiderMastermindEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/spidermastermind-texturemap.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SpiderMastermindEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/spidermastermind_animation.json");
	}
}