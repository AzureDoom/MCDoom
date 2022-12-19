package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.SpiderMastermindEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SpiderMastermindModel extends GeoModel<SpiderMastermindEntity> {

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

	@Override
	public RenderLayer getRenderType(SpiderMastermindEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}