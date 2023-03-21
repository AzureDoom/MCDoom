package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.MaykrDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class MaykrModel extends GeoModel<MaykrDoomArmor> {
	@Override
	public ResourceLocation getModelResource(MaykrDoomArmor object) {
		return DoomMod.modResource("geo/maykrarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MaykrDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/maykr_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MaykrDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}