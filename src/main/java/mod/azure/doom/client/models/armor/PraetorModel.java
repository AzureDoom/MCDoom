package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PraetorDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PraetorModel extends AnimatedGeoModel<PraetorDoomArmor> {
	@Override
	public ResourceLocation getModelResource(PraetorDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PraetorDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/praetor_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PraetorDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}