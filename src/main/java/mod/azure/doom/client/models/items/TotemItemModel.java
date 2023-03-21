package mod.azure.doom.client.models.items;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.TotemBlockItem;
import net.minecraft.resources.ResourceLocation;

public class TotemItemModel extends GeoModel<TotemBlockItem> {
	@Override
	public ResourceLocation getAnimationResource(TotemBlockItem entity) {
		return DoomMod.modResource("animations/totem.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(TotemBlockItem animatable) {
		return DoomMod.modResource("geo/totem.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TotemBlockItem entity) {
		return DoomMod.modResource("textures/block/totem.png");
	}
}
