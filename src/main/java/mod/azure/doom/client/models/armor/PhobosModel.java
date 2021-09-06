package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PhobosDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PhobosModel extends AnimatedGeoModel<PhobosDoomArmor> {
	@Override
	public Identifier getModelLocation(PhobosDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(PhobosDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/phobos_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(PhobosDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}