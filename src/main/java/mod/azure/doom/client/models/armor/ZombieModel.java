package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ZombieDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ZombieModel extends AnimatedGeoModel<ZombieDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(ZombieDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/zombiearmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ZombieDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/zombie_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ZombieDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}