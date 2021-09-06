package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.NightmareDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class NightmareModel extends AnimatedGeoModel<NightmareDoomArmor> {
	@Override
	public Identifier getModelLocation(NightmareDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomicornarmor.geo.json");
	}

	@Override
	public Identifier getTextureLocation(NightmareDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/nightmare_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationFileLocation(NightmareDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomicorn_animation.json");
	}
}