package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.TwentyFiveDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class TwentyFiveModel extends GeoModel<TwentyFiveDoomArmor> {
	@Override
	public ResourceLocation getModelResource(TwentyFiveDoomArmor object) {
		return DoomMod.modResource("geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TwentyFiveDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/twenty_five_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(TwentyFiveDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}