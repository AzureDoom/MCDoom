package mod.azure.doom.client.models.items;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.TotemBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GunCraftingItemModel extends AnimatedGeoModel<TotemBlockItem> {
	@Override
	public ResourceLocation getAnimationFileLocation(TotemBlockItem entity) {
		return new ResourceLocation(DoomMod.MODID, "animations/gun_table.animation.json");
	}

	@Override
	public ResourceLocation getModelLocation(TotemBlockItem animatable) {
		return new ResourceLocation(DoomMod.MODID, "geo/gun_table.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(TotemBlockItem entity) {
		return new ResourceLocation(DoomMod.MODID, "textures/blocks/gun_table.png");
	}
}
