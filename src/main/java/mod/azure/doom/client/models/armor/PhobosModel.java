package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PhobosDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class PhobosModel extends GeoModel<PhobosDoomArmor> {
	@Override
	public ResourceLocation getModelResource(PhobosDoomArmor object) {
		return DoomMod.modResource("geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PhobosDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/phobos_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PhobosDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}