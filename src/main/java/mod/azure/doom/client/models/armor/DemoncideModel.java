package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DemoncideDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DemoncideModel extends AnimatedGeoModel<DemoncideDoomArmor> {
	@Override
	public ResourceLocation getModelResource(DemoncideDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DemoncideDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/demoncide_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DemoncideDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}