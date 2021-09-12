package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PhobosDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PhobosModel extends AnimatedGeoModel<PhobosDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(PhobosDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(PhobosDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/phobos_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(PhobosDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}