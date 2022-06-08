package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.MaykrDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MaykrModel extends AnimatedGeoModel<MaykrDoomArmor> {
	@Override
	public ResourceLocation getModelResource(MaykrDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/maykrarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MaykrDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/maykr_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(MaykrDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}