package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.BronzeDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BronzeModel extends AnimatedGeoModel<BronzeDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(BronzeDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(BronzeDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/bronze_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(BronzeDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}