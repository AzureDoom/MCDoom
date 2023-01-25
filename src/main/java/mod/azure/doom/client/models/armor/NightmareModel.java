package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.NightmareDoomArmor;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class NightmareModel extends GeoModel<NightmareDoomArmor> {
	@Override
	public ResourceLocation getModelResource(NightmareDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomicornarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(NightmareDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/nightmare_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(NightmareDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/doomicorn_animation.json");
	}
}