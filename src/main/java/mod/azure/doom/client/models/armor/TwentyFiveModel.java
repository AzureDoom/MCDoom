package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.TwentyFiveDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class TwentyFiveModel extends AnimatedGeoModel<TwentyFiveDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(TwentyFiveDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(TwentyFiveDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/twenty_five_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(TwentyFiveDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}