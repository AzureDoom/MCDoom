package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.MulletDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Mullet1Model extends AnimatedGeoModel<MulletDoomArmor> {
	@Override
	public Identifier getModelLocation(MulletDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/mulletarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(MulletDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/redneck1_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(MulletDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}