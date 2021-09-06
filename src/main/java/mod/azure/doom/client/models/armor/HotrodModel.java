package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.HotrodDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HotrodModel extends AnimatedGeoModel<HotrodDoomArmor> {
	@Override
	public Identifier getModelLocation(HotrodDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(HotrodDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/hotrod_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(HotrodDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}