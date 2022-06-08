package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.CueBallEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class CueballModel extends AnimatedTickingGeoModel<CueBallEntity> {

	private static final ResourceLocation[] TEX = {
			new ResourceLocation(DoomMod.MODID, "textures/entity/cueball.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/cueball_flame_1.png")};

	@Override
	public ResourceLocation getModelResource(CueBallEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/cueball.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CueBallEntity object) {
		return TEX[(object.getFlameTimer())];
	}

	@Override
	public ResourceLocation getAnimationResource(CueBallEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/rocket.animation.json");
	}

}