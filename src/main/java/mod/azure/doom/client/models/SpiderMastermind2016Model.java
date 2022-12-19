package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.SpiderMastermind2016Entity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SpiderMastermind2016Model extends GeoModel<SpiderMastermind2016Entity> {

	@Override
	public Identifier getModelResource(SpiderMastermind2016Entity object) {
		return new Identifier(DoomMod.MODID, "geo/spidermastermind2016.geo.json");
	}

	@Override
	public Identifier getTextureResource(SpiderMastermind2016Entity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/spidermastermind2016.png");
	}

	@Override
	public Identifier getAnimationResource(SpiderMastermind2016Entity object) {
		return new Identifier(DoomMod.MODID, "animations/spidermastermind2016.animation.json");
	}

	@Override
	public RenderLayer getRenderType(SpiderMastermind2016Entity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}