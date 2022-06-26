package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.CueBallEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class CueballModel extends AnimatedTickingGeoModel<CueBallEntity> {

	private static final ResourceLocation[] TEX = { new ResourceLocation(DoomMod.MODID, "textures/entity/cueball.png"),
			new ResourceLocation(DoomMod.MODID, "textures/entity/cueball_flame_1.png") };

	@Override
	public ResourceLocation getModelLocation(CueBallEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"geo/" + (object.getVariant() == 2 ? "screecher" : "cueball") + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(CueBallEntity object) {
		return object.getVariant() == 2 ? new ResourceLocation(DoomMod.MODID, "textures/entity/screecher.png")
				: TEX[(object.getFlameTimer())];
	}

	@Override
	public ResourceLocation getAnimationFileLocation(CueBallEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"animations/" + (object.getVariant() == 2 ? "screecher" : "rocket") + ".animation.json");
	}

}