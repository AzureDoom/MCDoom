package mod.azure.doom.client.models.tile;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tileentity.TotemEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TotemModel extends AnimatedGeoModel<TotemEntity> {
	@Override
	public ResourceLocation getAnimationFileLocation(TotemEntity entity) {
		return new ResourceLocation(DoomMod.MODID, "animations/totem.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(TotemEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "geo/totem.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(TotemEntity entity) {
		return new ResourceLocation(DoomMod.MODID, "textures/blocks/totem.png");
	}
}
