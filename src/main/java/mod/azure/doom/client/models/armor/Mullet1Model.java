package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.MulletDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class Mullet1Model extends GeoModel<MulletDoomArmor> {
	@Override
	public ResourceLocation getModelResource(MulletDoomArmor object) {
		return DoomMod.modResource("geo/mulletarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MulletDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/redneck1_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MulletDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}