package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class DoomModel extends AnimatedGeoModel<DoomArmor> {
	@Override
	public Identifier getModelResource(DoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(DoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/doom_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(DoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}