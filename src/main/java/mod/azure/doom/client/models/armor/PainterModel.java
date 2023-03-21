package mod.azure.doom.client.models.armor;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PainterDoomArmor;
import net.minecraft.resources.ResourceLocation;

public class PainterModel extends GeoModel<PainterDoomArmor> {
	@Override
	public ResourceLocation getModelResource(PainterDoomArmor object) {
		return DoomMod.modResource("geo/painterarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PainterDoomArmor object) {
		return DoomMod.modResource("textures/models/armor/painter_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PainterDoomArmor animatable) {
		return DoomMod.modResource("animations/armor_animation.json");
	}
}