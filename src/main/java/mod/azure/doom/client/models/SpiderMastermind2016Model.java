package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.SpiderMastermind2016Entity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpiderMastermind2016Model extends AnimatedGeoModel<SpiderMastermind2016Entity> {

	@Override
	public Identifier getModelLocation(SpiderMastermind2016Entity object) {
		return new Identifier(DoomMod.MODID, "geo/spidermastermind2016.geo.json");
	}

	@Override
	public Identifier getTextureLocation(SpiderMastermind2016Entity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/spidermastermind2016.png");
	}

	@Override
	public Identifier getAnimationFileLocation(SpiderMastermind2016Entity object) {
		return new Identifier(DoomMod.MODID, "animations/spidermastermind2016.animation.json");
	}
}