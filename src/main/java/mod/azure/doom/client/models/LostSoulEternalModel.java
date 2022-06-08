package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class LostSoulEternalModel extends AnimatedTickingGeoModel<LostSoulEntity> {

	private static final ResourceLocation[] TEX = {
			new ResourceLocation(DoomMod.MODID, "textures/entity/lostsould_eternal_1.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lostsould_eternal_2.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lostsould_eternal_3.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lostsould_eternal_4.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lostsould_eternal_5.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lostsould_eternal_6.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lostsould_eternal_7.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/lostsould_eternal_8.png") };

	@Override
	public ResourceLocation getModelResource(LostSoulEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/lostsouleternal.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LostSoulEntity object) {
		return TEX[(object.getFlameTimer())];
	}

	@Override
	public ResourceLocation getAnimationResource(LostSoulEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/lostsoul_animation.json");
	}
}