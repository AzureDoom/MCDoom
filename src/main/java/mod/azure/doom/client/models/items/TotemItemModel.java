package mod.azure.doom.client.models.items;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.TotemBlockItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TotemItemModel extends GeoModel<TotemBlockItem> {
	@Override
	public Identifier getAnimationResource(TotemBlockItem entity) {
		return new Identifier(DoomMod.MODID, "animations/totem.animation.json");
	}

	@Override
	public Identifier getModelResource(TotemBlockItem animatable) {
		return new Identifier(DoomMod.MODID, "geo/totem.geo.json");
	}

	@Override
	public Identifier getTextureResource(TotemBlockItem entity) {
		return new Identifier(DoomMod.MODID, "textures/block/totem.png");
	}
}
