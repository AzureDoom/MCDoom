package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.MidnightDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class MidnightModel extends GeoModel<MidnightDoomArmor> {
	@Override
	public ResourceLocation getModelResource(MidnightDoomArmor object) {
		return DoomMod.modResource("geo/doom1armor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MidnightDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/midnight_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MidnightDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}