package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.ArachnotronEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class ArachonotronEternalModel extends AnimatedTickingGeoModel<ArachnotronEntity> {

	@Override
	public Identifier getModelResource(ArachnotronEntity object) {
		return new Identifier(DoomMod.MODID, "geo/arachonotroneternal.geo.json");
	}

	@Override
	public Identifier getTextureResource(ArachnotronEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/arachonotroneternal.png");
	}

	@Override
	public Identifier getAnimationResource(ArachnotronEntity object) {
		return new Identifier(DoomMod.MODID, "animations/arachonotroneternal.animation.json");
	}

}