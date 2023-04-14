package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.Mullet2DoomArmor;
import net.minecraft.resources.ResourceLocation;

public class Mullet2Model extends GeoModel<Mullet2DoomArmor> {
	@Override
	public ResourceLocation getModelResource(Mullet2DoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/mulletarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Mullet2DoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/redneck2_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Mullet2DoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}