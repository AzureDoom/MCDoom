package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DemoncideDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DemoncideModel extends AnimatedGeoModel<DemoncideDoomArmor> {
	@Override
	public Identifier getModelResource(DemoncideDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(DemoncideDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/demoncide_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(DemoncideDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}