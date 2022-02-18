package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class LostSoulEternalModel extends AnimatedTickingGeoModel<LostSoulEntity> {

	private static final Identifier[] TEX = {
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_1.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_2.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_3.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_4.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_5.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_6.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_7.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_8.png") };

	@Override
	public Identifier getModelLocation(LostSoulEntity object) {
		return new Identifier(DoomMod.MODID, "geo/lostsouleternal.geo.json");
	}

	@Override
	public Identifier getTextureLocation(LostSoulEntity object) {
		return TEX[(object.getFlameTimer())];
	}

	@Override
	public Identifier getAnimationFileLocation(LostSoulEntity object) {
		return new Identifier(DoomMod.MODID, "animations/lostsoul_animation.json");
	}
}