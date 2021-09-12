package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.MidnightDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MidnightModel extends AnimatedGeoModel<MidnightDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(MidnightDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doom1armor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(MidnightDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/midnight_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(MidnightDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}