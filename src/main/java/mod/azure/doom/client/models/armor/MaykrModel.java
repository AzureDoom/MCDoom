package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.MaykrDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MaykrModel extends GeoModel<MaykrDoomArmor> {
	@Override
	public Identifier getModelResource(MaykrDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/maykrarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(MaykrDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/maykr_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(MaykrDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}