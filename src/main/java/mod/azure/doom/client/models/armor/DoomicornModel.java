package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DoomicornDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DoomicornModel extends AnimatedGeoModel<DoomicornDoomArmor> {
	@Override
	public ResourceLocation getModelResource(DoomicornDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomicornarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DoomicornDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/doomicorn_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DoomicornDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/doomicorn_animation.json");
	}
}