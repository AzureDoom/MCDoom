package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.CultistDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CultistModel extends AnimatedGeoModel<CultistDoomArmor> {
	@Override
	public Identifier getModelLocation(CultistDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/cultistarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(CultistDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/cultist_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(CultistDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}