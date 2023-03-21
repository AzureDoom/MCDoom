package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.BronzeDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class BronzeModel extends GeoModel<BronzeDoomArmor> {
	@Override
	public ResourceLocation getModelResource(BronzeDoomArmor object) {
		return DoomMod.modResource("geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BronzeDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/bronze_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BronzeDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}