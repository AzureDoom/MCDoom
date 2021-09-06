package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ZombieDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ZombieModel extends AnimatedGeoModel<ZombieDoomArmor> {
	@Override
	public Identifier getModelLocation(ZombieDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/zombiearmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ZombieDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/zombie_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(ZombieDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}