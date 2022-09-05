package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.GoreNestEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class GoreNestModel extends AnimatedTickingGeoModel<GoreNestEntity> {

	@Override
	public Identifier getModelResource(GoreNestEntity object) {
		return new Identifier(DoomMod.MODID, "geo/gorenest.geo.json");
	}

	@Override
	public Identifier getTextureResource(GoreNestEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/gore_nest.png");
	}

	@Override
	public Identifier getAnimationResource(GoreNestEntity object) {
		return new Identifier(DoomMod.MODID, "animations/gorenest_animation.json");
	}
}