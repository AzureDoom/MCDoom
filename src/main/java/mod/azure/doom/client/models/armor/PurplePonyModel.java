package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PurplePonyDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PurplePonyModel extends AnimatedGeoModel<PurplePonyDoomArmor> {
	@Override
	public ResourceLocation getModelResource(PurplePonyDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomicornarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PurplePonyDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/purplepony_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PurplePonyDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/doomicorn_animation.json");
	}
}