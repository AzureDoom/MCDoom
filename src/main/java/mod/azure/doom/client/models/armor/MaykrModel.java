package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.MaykrDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MaykrModel extends AnimatedGeoModel<MaykrDoomArmor> {
	@Override
	public Identifier getModelLocation(MaykrDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/maykrarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(MaykrDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/maykr_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(MaykrDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}