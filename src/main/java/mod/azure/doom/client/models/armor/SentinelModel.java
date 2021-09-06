package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.SentinelDoomArmor;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SentinelModel extends AnimatedGeoModel<SentinelDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(SentinelDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/sentinelarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SentinelDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/sentinel_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(SentinelDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}