package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.Mullet2DoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class Mullet2Model extends AnimatedGeoModel<Mullet2DoomArmor> {
	@Override
	public ResourceLocation getModelLocation(Mullet2DoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/mulletarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(Mullet2DoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/redneck2_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(Mullet2DoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}