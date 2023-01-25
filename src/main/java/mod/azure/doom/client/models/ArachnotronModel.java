package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.ArachnotronEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

/**
 * Arachnotron - Batpixxler
 */
public class ArachnotronModel extends GeoModel<ArachnotronEntity> {

	public ArachnotronModel() {
	}

	@Override
	public ResourceLocation getModelResource(ArachnotronEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"geo/" + (object.getVariant() == 2 ? "arachnotron64" : "arachnotron") + ".geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ArachnotronEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"textures/entity/arachnotron-" + (object.getVariant() == 2 ? "64" : "texturemap") + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(ArachnotronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/"
				+ (object.getVariant() == 2 ? "arachnotron64.animation" : "arachnotron_walking") + ".json");
	}

	@Override
	public RenderType getRenderType(ArachnotronEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}