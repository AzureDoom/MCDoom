package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.ClassicBronzeDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class ClassicBronzeModel extends GeoModel<ClassicBronzeDoomArmor> {
	@Override
	public ResourceLocation getModelResource(ClassicBronzeDoomArmor object) {
		return DoomMod.modResource("geo/classicarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ClassicBronzeDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/classic_bronze_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ClassicBronzeDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}