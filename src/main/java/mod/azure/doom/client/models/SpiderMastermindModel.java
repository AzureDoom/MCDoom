package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpiderMastermindModel extends AnimatedGeoModel<SpiderMastermindEntity> {

	public SpiderMastermindModel() {
	}

	@Override
	public Identifier getModelLocation(SpiderMastermindEntity object) {
		return new Identifier(DoomMod.MODID, "geo/spidermastermind.geo.json");
	}

	@Override
	public Identifier getTextureLocation(SpiderMastermindEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/spidermastermind-texturemap.png");
	}

	@Override
	public Identifier getAnimationFileLocation(SpiderMastermindEntity object) {
		return new Identifier(DoomMod.MODID, "animations/spidermastermind_animation.json");
	}
}