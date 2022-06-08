package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.GoreNestEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class GoreNestModel extends AnimatedTickingGeoModel<GoreNestEntity> {

	public GoreNestModel() {
		this.getAnimationProcessor().getBone("effect");
	}

	@Override
	public ResourceLocation getModelResource(GoreNestEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/gorenest.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GoreNestEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/gore_nest.png");
	}

	@Override
	public ResourceLocation getAnimationResource(GoreNestEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/gorenest_animation.json");
	}
}