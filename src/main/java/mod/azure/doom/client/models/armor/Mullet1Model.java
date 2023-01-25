package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.MulletDoomArmor;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class Mullet1Model extends GeoModel<MulletDoomArmor> {
	@Override
	public ResourceLocation getModelResource(MulletDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/mulletarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MulletDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/redneck1_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MulletDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}