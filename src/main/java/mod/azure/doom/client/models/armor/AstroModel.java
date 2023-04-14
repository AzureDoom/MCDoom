package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.AstroDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class AstroModel extends GeoModel<AstroDoomArmor> {
	@Override
	public ResourceLocation getModelResource(AstroDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(AstroDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/astro_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(AstroDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}