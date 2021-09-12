package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ClassicIndigoDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ClassicIndigoModel extends AnimatedGeoModel<ClassicIndigoDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(ClassicIndigoDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/classicarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ClassicIndigoDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/classic_indigo_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ClassicIndigoDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}