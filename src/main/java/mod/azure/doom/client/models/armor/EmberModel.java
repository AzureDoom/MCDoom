package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.EmberDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EmberModel extends AnimatedGeoModel<EmberDoomArmor> {
	@Override
	public Identifier getModelLocation(EmberDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doom1armor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(EmberDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/ember_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(EmberDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}