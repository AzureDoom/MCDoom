package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.DoomicornDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class DoomicornModel extends AnimatedGeoModel<DoomicornDoomArmor> {
	@Override
	public Identifier getModelResource(DoomicornDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomicornarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(DoomicornDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/doomicorn_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(DoomicornDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomicorn_animation.json");
	}
}