package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PainterDoomArmor;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PainterModel extends AnimatedGeoModel<PainterDoomArmor> {
	@Override
	public ResourceLocation getModelLocation(PainterDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "geo/painterarmor.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(PainterDoomArmor object) {
		return new ResourceLocation(DoomMod.MODID, "textures/models/armor/painter_armor_layer_1.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(PainterDoomArmor animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/armor_animation.json");
	}
}