package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.SentinelHammerItem;
import net.minecraft.resources.ResourceLocation;

public class SentinelHammerModel extends GeoModel<SentinelHammerItem> {
	@Override
	public ResourceLocation getModelResource(SentinelHammerItem object) {
		return new ResourceLocation(DoomMod.MODID, "geo/sentinelhammer.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SentinelHammerItem object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/sentinel_hammer.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SentinelHammerItem animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/sentinelhammer.animation.json");
	}
}
