package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.AstroDoomArmor;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class AstroModel extends AnimatedGeoModel<AstroDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(AstroDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(AstroDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/astro_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(AstroDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}