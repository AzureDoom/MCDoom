package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.CueBallEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CueballModel extends AnimatedGeoModel<CueBallEntity> {

	private static final ResourceLocation[] TEX = {
			new ResourceLocation(DoomMod.MODID, "textures/entity/cueball.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/cueball_flame_1.png")};

	@Override
	public ResourceLocation getModelLocation(CueBallEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/cueball.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(CueBallEntity object) {
		return TEX[(object.getFlameTimer())];
	}

	@Override
	public ResourceLocation getAnimationFileLocation(CueBallEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/rocket.animation.json");
	}

}