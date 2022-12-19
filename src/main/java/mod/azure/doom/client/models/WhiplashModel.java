package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.WhiplashEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class WhiplashModel extends GeoModel<WhiplashEntity> {

	@Override
	public Identifier getModelResource(WhiplashEntity object) {
		return new Identifier(DoomMod.MODID, "geo/whiplash.geo.json");
	}

	@Override
	public Identifier getTextureResource(WhiplashEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/whiplash.png");
	}

	@Override
	public Identifier getAnimationResource(WhiplashEntity object) {
		return new Identifier(DoomMod.MODID, "animations/whiplash.animation.json");
	}

	@Override
	public RenderLayer getRenderType(WhiplashEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

}