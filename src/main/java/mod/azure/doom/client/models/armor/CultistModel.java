package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.CultistDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class CultistModel extends GeoModel<CultistDoomArmor> {
	@Override
	public ResourceLocation getModelResource(CultistDoomArmor object) {
		return DoomMod.modResource("geo/cultistarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CultistDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/cultist_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(CultistDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}