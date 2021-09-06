package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.EmberDoomArmor;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EmberModel extends AnimatedGeoModel<EmberDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(EmberDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doom1armor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(EmberDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/ember_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(EmberDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}