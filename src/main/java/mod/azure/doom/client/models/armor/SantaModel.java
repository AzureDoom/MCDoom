package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.SantaDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class SantaModel extends GeoModel<SantaDoomArmor> {
	@Override
	public ResourceLocation getModelResource(SantaDoomArmor object) {
		return DoomMod.modResource("geo/santaarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SantaDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/santa_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SantaDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}