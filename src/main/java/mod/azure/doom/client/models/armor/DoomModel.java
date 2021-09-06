package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DoomModel extends AnimatedGeoModel<DoomArmor> {
	@Override
	public Identifier getModelLocation(DoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(DoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/doom_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(DoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}