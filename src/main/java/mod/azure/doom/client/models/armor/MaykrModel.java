package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.MaykrDoomArmor;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MaykrModel extends AnimatedGeoModel<MaykrDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(MaykrDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/maykrarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(MaykrDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/maykr_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(MaykrDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}