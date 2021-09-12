package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.SantaDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SantaModel extends AnimatedGeoModel<SantaDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(SantaDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/santaarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SantaDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/santa_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(SantaDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}