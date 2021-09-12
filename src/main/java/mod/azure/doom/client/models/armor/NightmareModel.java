package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.NightmareDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NightmareModel extends AnimatedGeoModel<NightmareDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(NightmareDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomicornarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(NightmareDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/nightmare_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(NightmareDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/doomicorn_animation.json");
	}
}