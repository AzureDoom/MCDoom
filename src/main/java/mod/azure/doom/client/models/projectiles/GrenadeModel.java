package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.GrenadeEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class GrenadeModel extends GeoModel<GrenadeEntity> {
	@Override
	public ResourceLocation getModelResource(GrenadeEntity object) {
		return DoomMod.modResource("geo/doomed_grenade.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GrenadeEntity object) {
		return DoomMod.modResource("textures/item/doomed_grenade.png");
	}

	@Override
	public ResourceLocation getAnimationResource(GrenadeEntity animatable) {
		return DoomMod.modResource("animations/doomed_grenade.animation.json");
	}

	@Override
	public RenderType getRenderType(GrenadeEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
