package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.PistolItem;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class PistolModel extends GeoModel<PistolItem> {
	@Override
	public ResourceLocation getModelResource(PistolItem object) {
		return new ResourceLocation(DoomMod.MODID, "geo/pistol.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PistolItem object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/pistol.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PistolItem animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/pistol.animation.json");
	}
}
