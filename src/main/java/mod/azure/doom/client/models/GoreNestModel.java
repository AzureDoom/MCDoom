package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.GoreNestEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GoreNestModel extends AnimatedGeoModel<GoreNestEntity> {

	public GoreNestModel() {
		this.getAnimationProcessor().getBone("effect");
	}

	@Override
	public ResourceLocation getModelLocation(GoreNestEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/gorenest.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(GoreNestEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/gore_nest.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(GoreNestEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/gorenest_animation.json");
	}
}