package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.MaykrDroneEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedTickingGeoModel;

public class MaykrDroneModel extends AnimatedTickingGeoModel<MaykrDroneEntity> {

	@Override
	public Identifier getModelResource(MaykrDroneEntity object) {
		return new Identifier(DoomMod.MODID, "geo/maykrdrone.geo.json");
	}

	@Override
	public Identifier getTextureResource(MaykrDroneEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/maykrdrone_" + object.getVariant() + ".png");
	}

	@Override
	public Identifier getAnimationResource(MaykrDroneEntity object) {
		return new Identifier(DoomMod.MODID, "animations/maykrdrone.animation.json");
	}
}
