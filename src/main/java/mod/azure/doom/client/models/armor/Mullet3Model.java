package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.Mullet3DoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Mullet3Model extends AnimatedGeoModel<Mullet3DoomArmor> {
	@Override
	public Identifier getModelLocation(Mullet3DoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/mulletarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(Mullet3DoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/redneck3_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(Mullet3DoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}