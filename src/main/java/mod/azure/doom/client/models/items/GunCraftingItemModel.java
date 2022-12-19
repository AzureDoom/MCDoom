package mod.azure.doom.client.models.items;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.GuntableBlockItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GunCraftingItemModel extends GeoModel<GuntableBlockItem> {
	@Override
	public Identifier getAnimationResource(GuntableBlockItem entity) {
		return new Identifier(DoomMod.MODID, "animations/gun_table.animation.json");
	}

	@Override
	public Identifier getModelResource(GuntableBlockItem animatable) {
		return new Identifier(DoomMod.MODID, "geo/gun_table.geo.json");
	}

	@Override
	public Identifier getTextureResource(GuntableBlockItem entity) {
		return new Identifier(DoomMod.MODID, "textures/block/gun_table.png");
	}
}
