package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DoomicornDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class DoomicornModel extends GeoModel<DoomicornDoomArmor> {
	@Override
	public ResourceLocation getModelResource(DoomicornDoomArmor object) {
		return DoomMod.modResource("geo/doomicornarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DoomicornDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/doomicorn_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DoomicornDoomArmor animatable) {
		return DoomMod.modResource("animations/doomicorn_animation.json");
	}
}