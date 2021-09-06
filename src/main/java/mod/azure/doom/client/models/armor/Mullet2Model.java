package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.Mullet2DoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Mullet2Model extends AnimatedGeoModel<Mullet2DoomArmor> {
	@Override
	public Identifier getModelLocation(Mullet2DoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/mulletarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(Mullet2DoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/redneck2_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(Mullet2DoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}