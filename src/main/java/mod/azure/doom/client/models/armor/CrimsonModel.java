package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.CrimsonDoomArmor;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class CrimsonModel extends GeoModel<CrimsonDoomArmor> {
	@Override
	public ResourceLocation getModelResource(CrimsonDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CrimsonDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/crimson_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(CrimsonDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}