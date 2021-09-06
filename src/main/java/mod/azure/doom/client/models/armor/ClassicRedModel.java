package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ClassicRedDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ClassicRedModel extends AnimatedGeoModel<ClassicRedDoomArmor> {
	@Override
	public Identifier getModelLocation(ClassicRedDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/classicarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ClassicRedDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/classic_red_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(ClassicRedDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}