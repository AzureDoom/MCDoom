package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedTickingGeoModel;

public class SpiderMastermindModel extends AnimatedTickingGeoModel<SpiderMastermindEntity> {

	@Override
	public Identifier getModelResource(SpiderMastermindEntity object) {
		return new Identifier(DoomMod.MODID, "geo/spidermastermind.geo.json");
	}

	@Override
	public Identifier getTextureResource(SpiderMastermindEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/spidermastermind-texturemap.png");
	}

	@Override
	public Identifier getAnimationResource(SpiderMastermindEntity object) {
		return new Identifier(DoomMod.MODID, "animations/spidermastermind_animation.json");
	}
}