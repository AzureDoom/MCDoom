package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DoomArmor;
import net.minecraft.resources.ResourceLocation;

public class DoomModel extends GeoModel<DoomArmor> {
	@Override
	public ResourceLocation getModelResource(DoomArmor object) {
		return DoomMod.modResource("geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DoomArmor object) {
		return DoomMod.modResource("textures/models/armor/doom_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}