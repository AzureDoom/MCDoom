package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.NightmareDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class NightmareModel extends GeoModel<NightmareDoomArmor> {
	@Override
	public ResourceLocation getModelResource(NightmareDoomArmor object) {
		return DoomMod.modResource("geo/doomicornarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(NightmareDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/nightmare_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(NightmareDoomArmor animatable) {
		return DoomMod.modResource("animations/doomicorn_animation.json");
	}
}