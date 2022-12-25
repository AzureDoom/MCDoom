package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PainterDoomArmor;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class PainterModel extends GeoModel<PainterDoomArmor> {
	@Override
	public ResourceLocation getModelResource(PainterDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/painterarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PainterDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/painter_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PainterDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}