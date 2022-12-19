package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.TwentyFiveDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TwentyFiveModel extends GeoModel<TwentyFiveDoomArmor> {
	@Override
	public Identifier getModelResource(TwentyFiveDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(TwentyFiveDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/twenty_five_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(TwentyFiveDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}