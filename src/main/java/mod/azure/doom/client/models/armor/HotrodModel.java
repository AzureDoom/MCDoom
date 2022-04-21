package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.HotrodDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class HotrodModel extends AnimatedGeoModel<HotrodDoomArmor> {
	@Override
	public Identifier getModelResource(HotrodDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(HotrodDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/hotrod_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(HotrodDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}