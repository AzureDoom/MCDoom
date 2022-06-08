package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ArchvileFiringModel extends AnimatedGeoModel<DoomFireEntity> {
	@Override
	public ResourceLocation getModelResource(DoomFireEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/archvilefiring.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DoomFireEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/empty.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DoomFireEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/archvilefiring.animation.json");
	}
}
