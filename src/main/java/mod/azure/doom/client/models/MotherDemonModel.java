package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MotherDemonModel extends AnimatedGeoModel<MotherDemonEntity> {

	@Override
	public Identifier getModelLocation(MotherDemonEntity object) {
		return new Identifier(DoomMod.MODID, "geo/motherdemon.geo.json");
	}

	@Override
	public Identifier getTextureLocation(MotherDemonEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/motherdemon.png");
	}

	@Override
	public Identifier getAnimationFileLocation(MotherDemonEntity object) {
		return new Identifier(DoomMod.MODID, "animations/motherdemon.animation.json");
	}
}