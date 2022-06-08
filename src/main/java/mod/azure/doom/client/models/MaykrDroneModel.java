package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierfodder.MaykrDroneEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class MaykrDroneModel extends AnimatedTickingGeoModel<MaykrDroneEntity> {

	@Override
	public ResourceLocation getModelResource(MaykrDroneEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/maykrdrone.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MaykrDroneEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/maykrdrone_" + object.getVariant() + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(MaykrDroneEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/maykrdrone.animation.json");
	}
}
