package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ClassicRedDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ClassicRedModel extends AnimatedGeoModel<ClassicRedDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(ClassicRedDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/classicarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ClassicRedDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/classic_red_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ClassicRedDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}