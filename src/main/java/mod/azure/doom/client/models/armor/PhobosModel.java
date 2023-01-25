package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PhobosDoomArmor;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class PhobosModel extends GeoModel<PhobosDoomArmor> {
	@Override
	public ResourceLocation getModelResource(PhobosDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PhobosDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/phobos_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PhobosDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}