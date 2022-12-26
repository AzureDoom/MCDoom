package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.SantaDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SantaModel extends GeoModel<SantaDoomArmor> {
	@Override
	public ResourceLocation getModelResource(SantaDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/santaarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SantaDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/santa_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SantaDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}