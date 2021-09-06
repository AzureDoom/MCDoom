package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.MidnightDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MidnightModel extends AnimatedGeoModel<MidnightDoomArmor> {
	@Override
	public Identifier getModelLocation(MidnightDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doom1armor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(MidnightDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/midnight_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(MidnightDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}