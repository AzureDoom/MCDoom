package mod.azure.doom.client.models.items;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.DoomBlockItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class GunCraftingItemModel extends AnimatedGeoModel<DoomBlockItem> {
	@Override
	public Identifier getAnimationResource(DoomBlockItem entity) {
		return new Identifier(DoomMod.MODID, "animations/gun_table.animation.json");
	}

	@Override
	public Identifier getModelResource(DoomBlockItem animatable) {
		return new Identifier(DoomMod.MODID, "geo/gun_table.geo.json");
	}

	@Override
	public Identifier getTextureResource(DoomBlockItem entity) {
		return new Identifier(DoomMod.MODID, "textures/blocks/gun_table.png");
	}
}
