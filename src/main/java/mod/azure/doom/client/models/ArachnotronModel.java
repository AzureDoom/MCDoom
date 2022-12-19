package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.ArachnotronEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

/**
 * Arachnotron - Batpixxler
 */
public class ArachnotronModel extends GeoModel<ArachnotronEntity> {

	public ArachnotronModel() {
	}

	@Override
	public Identifier getModelResource(ArachnotronEntity object) {
		return new Identifier(DoomMod.MODID,
				"geo/" + (object.getVariant() == 2 ? "arachnotron64" : "arachnotron") + ".geo.json");
	}

	@Override
	public Identifier getTextureResource(ArachnotronEntity object) {
		return new Identifier(DoomMod.MODID,
				"textures/entity/arachnotron-" + (object.getVariant() == 2 ? "64" : "texturemap") + ".png");
	}

	@Override
	public Identifier getAnimationResource(ArachnotronEntity object) {
		return new Identifier(DoomMod.MODID, "animations/"
				+ (object.getVariant() == 2 ? "arachnotron64.animation" : "arachnotron_walking") + ".json");
	}

	@Override
	public RenderLayer getRenderType(ArachnotronEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

}