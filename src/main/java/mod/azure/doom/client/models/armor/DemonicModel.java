package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DemonicDoomArmor;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class DemonicModel extends GeoModel<DemonicDoomArmor> {
	@Override
	public ResourceLocation getModelResource(DemonicDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doom1armor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DemonicDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/demonic_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DemonicDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}