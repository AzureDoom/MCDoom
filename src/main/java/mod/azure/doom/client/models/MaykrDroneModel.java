package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.MaykrDroneEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MaykrDroneModel extends AnimatedGeoModel<MaykrDroneEntity> {

	@Override
	public Identifier getModelLocation(MaykrDroneEntity object) {
		return new Identifier(DoomMod.MODID, "geo/maykrdrone.geo.json");
	}

	@Override
	public Identifier getTextureLocation(MaykrDroneEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/maykrdrone_" + object.getVariant() + ".png");
	}

	@Override
	public Identifier getAnimationFileLocation(MaykrDroneEntity object) {
		return new Identifier(DoomMod.MODID, "animations/maykrdrone.animation.json");
	}
}
