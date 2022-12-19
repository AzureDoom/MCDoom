package mod.azure.doom.client.models.items;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.GrenadeItem;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GrenadeItemModel extends GeoModel<GrenadeItem> {
	@Override
	public Identifier getModelResource(GrenadeItem object) {
		return new Identifier(DoomMod.MODID, "geo/doomed_grenade.geo.json");
	}

	@Override
	public Identifier getTextureResource(GrenadeItem object) {
		return new Identifier(DoomMod.MODID, "textures/item/doomed_grenade.png");
	}

	@Override
	public Identifier getAnimationResource(GrenadeItem animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomed_grenade.animation.json");
	}
}
