package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.SentinelDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class SentinelModel extends GeoModel<SentinelDoomArmor> {
	@Override
	public ResourceLocation getModelResource(SentinelDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/sentinelarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SentinelDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/sentinel_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SentinelDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}