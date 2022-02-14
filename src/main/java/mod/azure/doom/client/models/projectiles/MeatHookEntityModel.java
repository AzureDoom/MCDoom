package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.MeatHookEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MeatHookEntityModel extends AnimatedGeoModel<MeatHookEntity> {
	@Override
	public ResourceLocation getModelLocation(MeatHookEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/archvilefiring.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(MeatHookEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/supershotgun.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(MeatHookEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/chainblade.animation.json");
	}
}
