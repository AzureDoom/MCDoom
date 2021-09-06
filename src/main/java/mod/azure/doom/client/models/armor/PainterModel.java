package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PainterDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PainterModel extends AnimatedGeoModel<PainterDoomArmor> {
	@Override
	public Identifier getModelLocation(PainterDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/painterarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(PainterDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/painter_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(PainterDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}