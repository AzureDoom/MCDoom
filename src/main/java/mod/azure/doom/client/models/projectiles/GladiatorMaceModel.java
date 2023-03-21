package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.GladiatorMaceEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class GladiatorMaceModel extends GeoModel<GladiatorMaceEntity> {
	@Override
	public ResourceLocation getModelResource(GladiatorMaceEntity object) {
		return DoomMod.modResource("geo/gladiator_mace.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GladiatorMaceEntity object) {
		return DoomMod.modResource("textures/entity/gladiator.png");
	}

	@Override
	public ResourceLocation getAnimationResource(GladiatorMaceEntity animatable) {
		return DoomMod.modResource("animations/gladiator_mace.animation.json");
	}

	@Override
	public RenderType getRenderType(GladiatorMaceEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
