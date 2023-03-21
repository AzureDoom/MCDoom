package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DemonicDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class DemonicModel extends GeoModel<DemonicDoomArmor> {
	@Override
	public ResourceLocation getModelResource(DemonicDoomArmor object) {
		return DoomMod.modResource("geo/doom1armor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DemonicDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/demonic_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DemonicDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}