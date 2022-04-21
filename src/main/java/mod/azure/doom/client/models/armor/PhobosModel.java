package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PhobosDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class PhobosModel extends AnimatedGeoModel<PhobosDoomArmor> {
	@Override
	public Identifier getModelResource(PhobosDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(PhobosDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/phobos_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(PhobosDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/armor_animation.json");
	}
}