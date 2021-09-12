package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ClassicBronzeDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ClassicBronzeModel extends AnimatedGeoModel<ClassicBronzeDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(ClassicBronzeDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/classicarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ClassicBronzeDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/classic_bronze_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ClassicBronzeDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}