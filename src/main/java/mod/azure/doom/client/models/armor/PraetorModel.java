package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PraetorDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PraetorModel extends AnimatedGeoModel<PraetorDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(PraetorDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(PraetorDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/praetor_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(PraetorDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}