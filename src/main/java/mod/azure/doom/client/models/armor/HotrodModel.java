package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.HotrodDoomArmor;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HotrodModel extends AnimatedGeoModel<HotrodDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(HotrodDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(HotrodDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/hotrod_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(HotrodDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}