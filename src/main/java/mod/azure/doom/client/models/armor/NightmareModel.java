package mod.azure.doom.client.models.armor;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.armor.NightmareDoomArmor;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class NightmareModel extends GeoModel<NightmareDoomArmor> {
	@Override
	public Identifier getModelResource(NightmareDoomArmor object) {
		return new Identifier(DoomMod.MODID, "geo/doomicornarmor.geo.json");
	}

	@Override
	public Identifier getTextureResource(NightmareDoomArmor object) {
		return new Identifier(DoomMod.MODID, "textures/models/armor/nightmare_armor_layer_1.png");
	}

	@Override
	public Identifier getAnimationResource(NightmareDoomArmor animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomicorn_animation.json");
	}
}