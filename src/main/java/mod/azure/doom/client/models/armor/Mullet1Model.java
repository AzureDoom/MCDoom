package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.MulletDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Mullet1Model extends AnimatedGeoModel<MulletDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(MulletDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/mulletarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(MulletDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/redneck1_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(MulletDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}