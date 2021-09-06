package mod.azure.doom.client.models.items;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.DoomBlockItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TotemItemModel extends AnimatedGeoModel<DoomBlockItem> {
	@Override
	public ResourceLocation getAnimationFileLocation(DoomBlockItem entity) {
		return new ResourceLocation(DoomMod.MODID, "animations/totem.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(DoomBlockItem animatable) {
		return new ResourceLocation(DoomMod.MODID, "geo/totem.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(DoomBlockItem entity) {
		return new ResourceLocation(DoomMod.MODID, "textures/blocks/totem.png");
	}
}
