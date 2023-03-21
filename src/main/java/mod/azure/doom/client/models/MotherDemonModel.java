package mod.azure.doom.client.models;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class MotherDemonModel extends GeoModel<MotherDemonEntity> {

	@Override
	public ResourceLocation getModelResource(MotherDemonEntity object) {
		return DoomMod.modResource("geo/motherdemon.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MotherDemonEntity object) {
		return DoomMod.modResource("textures/entity/motherdemon.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MotherDemonEntity object) {
		return DoomMod.modResource("animations/motherdemon.animation.json");
	}

	@Override
	public RenderType getRenderType(MotherDemonEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}