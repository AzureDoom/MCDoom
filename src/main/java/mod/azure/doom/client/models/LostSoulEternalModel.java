package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedTickingGeoModel;

public class LostSoulEternalModel extends AnimatedTickingGeoModel<LostSoulEntity> {

	private static final Identifier[] TEX = { new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_1.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_2.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_3.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_4.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_5.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_6.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_7.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_eternal_8.png") };

	private static final Identifier[] TEX1 = { new Identifier(DoomMod.MODID, "textures/entity/lostsould_2016_1.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_2016_2.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_2016_3.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_2016_4.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_2016_5.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_2016_6.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_2016_7.png"),
			new Identifier(DoomMod.MODID, "textures/entity/lostsould_2016_8.png") };

	@Override
	public Identifier getModelResource(LostSoulEntity object) {
		return new Identifier(DoomMod.MODID, "geo/lostsouleternal.geo.json");
	}

	@Override
	public Identifier getTextureResource(LostSoulEntity object) {
		return object.getVariant() == 2 ? TEX1[(object.getFlameTimer())] : TEX[(object.getFlameTimer())];
	}

	@Override
	public Identifier getAnimationResource(LostSoulEntity object) {
		return new Identifier(DoomMod.MODID, "animations/lostsoul_animation.json");
	}
}