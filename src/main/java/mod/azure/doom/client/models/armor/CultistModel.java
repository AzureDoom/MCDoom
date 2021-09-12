package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.CultistDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CultistModel extends AnimatedGeoModel<CultistDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(CultistDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/cultistarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(CultistDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/cultist_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(CultistDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}