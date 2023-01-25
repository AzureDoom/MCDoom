package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ClassicIndigoDoomArmor;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class ClassicIndigoModel extends GeoModel<ClassicIndigoDoomArmor> {
	@Override
	public ResourceLocation getModelResource(ClassicIndigoDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/classicarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ClassicIndigoDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/classic_indigo_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ClassicIndigoDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}