package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.GoldDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GoldModel extends GeoModel<GoldDoomArmor> {
	@Override
	public ResourceLocation getModelResource(GoldDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GoldDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/gold_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(GoldDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}