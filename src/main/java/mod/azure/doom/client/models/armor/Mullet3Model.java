package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.Mullet3DoomArmor;
import net.minecraft.resources.ResourceLocation;

public class Mullet3Model extends GeoModel<Mullet3DoomArmor> {
	@Override
	public ResourceLocation getModelResource(Mullet3DoomArmor object) {
		return DoomMod.modResource("geo/mulletarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Mullet3DoomArmor object) {
		return DoomMod.modResource("textures/models/armor/redneck3_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Mullet3DoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}