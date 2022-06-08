package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.MeatHookEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MeatHookEntityModel extends AnimatedGeoModel<MeatHookEntity> {
	@Override
	public ResourceLocation getModelResource(MeatHookEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/archvilefiring.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MeatHookEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/supershotgun.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MeatHookEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/chainblade.animation.json");
	}
}
