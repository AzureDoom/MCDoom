package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.SentinelDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SentinelModel extends AnimatedGeoModel<SentinelDoomArmor> {
	@Override
	public Identifier getModelLocation(SentinelDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/sentinelarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(SentinelDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/sentinel_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(SentinelDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}