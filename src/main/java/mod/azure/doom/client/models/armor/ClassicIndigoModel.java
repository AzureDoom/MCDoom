package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ClassicIndigoDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class ClassicIndigoModel extends GeoModel<ClassicIndigoDoomArmor> {
	@Override
	public ResourceLocation getModelResource(ClassicIndigoDoomArmor object) {
		return DoomMod.modResource("geo/classicarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ClassicIndigoDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/classic_indigo_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ClassicIndigoDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}