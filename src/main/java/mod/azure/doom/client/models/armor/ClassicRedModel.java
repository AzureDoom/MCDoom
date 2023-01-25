package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ClassicRedDoomArmor;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class ClassicRedModel extends GeoModel<ClassicRedDoomArmor> {
	@Override
	public ResourceLocation getModelResource(ClassicRedDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/classicarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ClassicRedDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/classic_red_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ClassicRedDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}