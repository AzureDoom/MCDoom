package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ZombieDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class ZombieModel extends AnimatedGeoModel<ZombieDoomArmor> {
	@Override
	public Identifier getModelResource(ZombieDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/zombiearmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(ZombieDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/zombie_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(ZombieDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}