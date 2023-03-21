package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.PistolItem;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class PistolModel extends GeoModel<PistolItem> {
	@Override
	public ResourceLocation getModelResource(PistolItem object) {
		return DoomMod.modResource("geo/pistol.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PistolItem object) {
		return DoomMod.modResource("textures/item/pistol.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PistolItem animatable) {
		return DoomMod.modResource("animations/pistol.animation.json");
	}
}
