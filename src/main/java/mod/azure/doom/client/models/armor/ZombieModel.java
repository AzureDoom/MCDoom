package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ZombieDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class ZombieModel extends GeoModel<ZombieDoomArmor> {
	@Override
	public ResourceLocation getModelResource(ZombieDoomArmor object) {
		return DoomMod.modResource("geo/zombiearmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ZombieDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/zombie_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ZombieDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}