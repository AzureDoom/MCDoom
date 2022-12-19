package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.PlasmaGun;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PlasmagunModel extends GeoModel<PlasmaGun> {
	@Override
	public Identifier getModelResource(PlasmaGun object) {
		return new Identifier(DoomMod.MODID, "geo/plasmagun.geo.json");
	}

	@Override
	public Identifier getTextureResource(PlasmaGun object) {
		return new Identifier(DoomMod.MODID, "textures/item/rifle.png");
	}

	@Override
	public Identifier getAnimationResource(PlasmaGun animatable) {
		return new Identifier(DoomMod.MODID, "animations/plasmagun.animation.json");
	}
}
