package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.CrimsonDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CrimsonModel extends GeoModel<CrimsonDoomArmor> {
	@Override
	public Identifier getModelResource(CrimsonDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(CrimsonDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/crimson_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(CrimsonDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}