package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.PurplePonyDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PurplePonyModel extends AnimatedGeoModel<PurplePonyDoomArmor> {
	@Override
	public Identifier getModelResource(PurplePonyDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomicornarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(PurplePonyDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/purplepony_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(PurplePonyDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomicorn_animation.json");
	}
}