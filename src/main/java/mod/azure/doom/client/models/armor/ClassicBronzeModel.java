package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ClassicBronzeDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ClassicBronzeModel extends AnimatedGeoModel<ClassicBronzeDoomArmor> {
	@Override
	public Identifier getModelLocation(ClassicBronzeDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/classicarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ClassicBronzeDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/classic_bronze_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(ClassicBronzeDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}