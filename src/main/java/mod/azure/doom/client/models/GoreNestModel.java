package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierambient.GoreNestEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GoreNestModel extends GeoModel<GoreNestEntity> {

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

	@Override
	public RenderLayer getRenderType(GoreNestEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}