package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.CrimsonDoomArmor;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CrimsonModel extends AnimatedGeoModel<CrimsonDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(CrimsonDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(CrimsonDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/crimson_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(CrimsonDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}