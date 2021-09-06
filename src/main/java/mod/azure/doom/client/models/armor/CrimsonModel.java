package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.CrimsonDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CrimsonModel extends AnimatedGeoModel<CrimsonDoomArmor> {
	@Override
	public Identifier getModelLocation(CrimsonDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(CrimsonDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/crimson_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(CrimsonDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}