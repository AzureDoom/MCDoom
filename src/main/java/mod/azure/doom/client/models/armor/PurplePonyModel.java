package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PurplePonyDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class PurplePonyModel extends GeoModel<PurplePonyDoomArmor> {
	@Override
	public ResourceLocation getModelResource(PurplePonyDoomArmor object) {
		return DoomMod.modResource("geo/doomicornarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PurplePonyDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/purplepony_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PurplePonyDoomArmor animatable) {
		return DoomMod.modResource("animations/doomicorn_animation.json");
	}
}