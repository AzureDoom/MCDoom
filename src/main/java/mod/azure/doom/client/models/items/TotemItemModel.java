package mod.azure.doom.client.models.items;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.TotemBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TotemItemModel extends AnimatedGeoModel<TotemBlockItem> {
	@Override
	public ResourceLocation getAnimationResource(TotemBlockItem entity) {
		return new ResourceLocation(DoomMod.MODID, "animations/totem.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TotemBlockItem animatable) {
		return new ResourceLocation(DoomMod.MODID, "geo/totem.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TotemBlockItem entity) {
		return new ResourceLocation(DoomMod.MODID, "textures/blocks/totem.png");
	}
}
