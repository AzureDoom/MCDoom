package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.SentinelHammerItem;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class SentinelHammerModel extends GeoModel<SentinelHammerItem> {
	@Override
	public ResourceLocation getModelResource(SentinelHammerItem object) {
		return DoomMod.modResource("geo/sentinelhammer.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SentinelHammerItem object) {
		return DoomMod.modResource("textures/item/sentinel_hammer.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SentinelHammerItem animatable) {
		return DoomMod.modResource("animations/sentinelhammer.animation.json");
	}
}
