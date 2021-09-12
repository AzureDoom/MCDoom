package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ClassicDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ClassicModel extends AnimatedGeoModel<ClassicDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(ClassicDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/classicarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ClassicDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/classic_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ClassicDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}