package mod.azure.doom.client.models.items;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.GrenadeItem;
import net.minecraft.resources.ResourceLocation;

public class GrenadeItemModel extends GeoModel<GrenadeItem> {
	@Override
	public ResourceLocation getModelResource(GrenadeItem object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomed_grenade.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GrenadeItem object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/doomed_grenade.png");
	}

	@Override
	public ResourceLocation getAnimationResource(GrenadeItem animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/doomed_grenade.animation.json");
	}
}
