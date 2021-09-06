package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PurplePonyDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PurplePonyModel extends AnimatedGeoModel<PurplePonyDoomArmor> {
	@Override
	public Identifier getModelLocation(PurplePonyDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomicornarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(PurplePonyDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/purplepony_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(PurplePonyDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomicorn_animation.json");
	}
}