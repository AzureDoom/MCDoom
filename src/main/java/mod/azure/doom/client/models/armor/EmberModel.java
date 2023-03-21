package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.EmberDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class EmberModel extends GeoModel<EmberDoomArmor> {
	@Override
	public ResourceLocation getModelResource(EmberDoomArmor object) {
		return DoomMod.modResource("geo/doom1armor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(EmberDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/ember_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(EmberDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}