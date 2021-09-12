package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.Mullet3DoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Mullet3Model extends AnimatedGeoModel<Mullet3DoomArmor> {
	@Override
	public ResourceLocation getModelLocation(Mullet3DoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/mulletarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(Mullet3DoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/redneck3_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(Mullet3DoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}