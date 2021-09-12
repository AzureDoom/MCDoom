package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.WhiplashEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WhiplashModel extends AnimatedGeoModel<WhiplashEntity> {

	@Override
	public ResourceLocation getModelLocation(WhiplashEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/whiplash.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(WhiplashEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/whiplash.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(WhiplashEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/whiplash.animation.json");
	}

}