package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.SantaDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SantaModel extends AnimatedGeoModel<SantaDoomArmor> {
	@Override
	public Identifier getModelLocation(SantaDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/santaarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(SantaDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/santa_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(SantaDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}