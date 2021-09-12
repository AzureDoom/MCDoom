package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DoomModel extends AnimatedGeoModel<DoomArmor> {
	@Override
	public ResourceLocation getModelLocation(DoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(DoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/doom_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(DoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}