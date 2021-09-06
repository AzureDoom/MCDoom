package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ClassicDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ClassicModel extends AnimatedGeoModel<ClassicDoomArmor> {
	@Override
	public Identifier getModelLocation(ClassicDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/classicarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ClassicDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/classic_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(ClassicDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}