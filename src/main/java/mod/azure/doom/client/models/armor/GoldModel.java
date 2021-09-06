package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.GoldDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GoldModel extends AnimatedGeoModel<GoldDoomArmor> {
	@Override
	public Identifier getModelLocation(GoldDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(GoldDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/gold_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(GoldDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}