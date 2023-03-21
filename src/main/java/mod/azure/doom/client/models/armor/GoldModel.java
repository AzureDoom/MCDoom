package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.GoldDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class GoldModel extends GeoModel<GoldDoomArmor> {
	@Override
	public ResourceLocation getModelResource(GoldDoomArmor object) {
		return DoomMod.modResource("geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GoldDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/gold_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(GoldDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}