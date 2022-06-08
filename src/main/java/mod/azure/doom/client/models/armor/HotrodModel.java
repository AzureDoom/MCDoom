package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.HotrodDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HotrodModel extends AnimatedGeoModel<HotrodDoomArmor> {
	@Override
	public ResourceLocation getModelResource(HotrodDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HotrodDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/hotrod_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(HotrodDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}