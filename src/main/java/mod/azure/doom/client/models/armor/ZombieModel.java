package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ZombieDoomArmor;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class ZombieModel extends GeoModel<ZombieDoomArmor> {
	@Override
	public ResourceLocation getModelResource(ZombieDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/zombiearmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ZombieDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/zombie_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ZombieDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}