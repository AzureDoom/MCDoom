package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.BronzeDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BronzeModel extends AnimatedGeoModel<BronzeDoomArmor> {
	@Override
	public Identifier getModelLocation(BronzeDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(BronzeDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/bronze_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(BronzeDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}