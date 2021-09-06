package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ClassicIndigoDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ClassicIndigoModel extends AnimatedGeoModel<ClassicIndigoDoomArmor> {
	@Override
	public Identifier getModelLocation(ClassicIndigoDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/classicarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ClassicIndigoDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/classic_indigo_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(ClassicIndigoDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}