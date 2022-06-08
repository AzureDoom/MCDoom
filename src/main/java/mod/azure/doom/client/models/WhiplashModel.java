package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.WhiplashEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class WhiplashModel extends AnimatedTickingGeoModel<WhiplashEntity> {

	@Override
	public ResourceLocation getModelResource(WhiplashEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/whiplash.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(WhiplashEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/whiplash.png");
	}

	@Override
	public ResourceLocation getAnimationResource(WhiplashEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/whiplash.animation.json");
	}

}