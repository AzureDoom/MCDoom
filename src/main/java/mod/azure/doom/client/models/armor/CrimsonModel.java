package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.CrimsonDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class CrimsonModel extends GeoModel<CrimsonDoomArmor> {
	@Override
	public ResourceLocation getModelResource(CrimsonDoomArmor object) {
		return DoomMod.modResource("geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CrimsonDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/crimson_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(CrimsonDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}