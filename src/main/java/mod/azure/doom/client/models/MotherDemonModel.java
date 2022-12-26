package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierboss.MotherDemonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MotherDemonModel extends GeoModel<MotherDemonEntity> {

	@Override
	public ResourceLocation getModelResource(MotherDemonEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/motherdemon.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MotherDemonEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/motherdemon.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MotherDemonEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/motherdemon.animation.json");
	}

	@Override
	public RenderType getRenderType(MotherDemonEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}