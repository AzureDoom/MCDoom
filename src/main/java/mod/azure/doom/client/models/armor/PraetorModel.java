package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PraetorDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PraetorModel extends AnimatedGeoModel<PraetorDoomArmor> {
	@Override
	public Identifier getModelLocation(PraetorDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(PraetorDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/praetor_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(PraetorDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}