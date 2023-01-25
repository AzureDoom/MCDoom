package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.MeatHookEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class MeatHookEntityModel extends GeoModel<MeatHookEntity> {
	@Override
	public ResourceLocation getModelResource(MeatHookEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/archvilefiring.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MeatHookEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/supershotgun.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MeatHookEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/chainblade.animation.json");
	}

	@Override
	public RenderType getRenderType(MeatHookEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
