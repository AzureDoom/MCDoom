package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.GoldDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class GoldModel extends AnimatedGeoModel<GoldDoomArmor> {
	@Override
	public Identifier getModelResource(GoldDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(GoldDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/gold_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(GoldDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}