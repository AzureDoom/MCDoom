package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.ChainBladeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChainBladeModel extends AnimatedGeoModel<ChainBladeEntity> {
	@Override
	public ResourceLocation getModelLocation(ChainBladeEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/chainblade.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ChainBladeEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/whiplash.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ChainBladeEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/chainblade.animation.json");
	}
}
