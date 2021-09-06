package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.PlasmaGun;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PlasmagunModel extends AnimatedGeoModel<PlasmaGun> {
	@Override
	public Identifier getModelLocation(PlasmaGun object) {
		return new Identifier(DoomMod.MODID, "geo/plasmagun.geo.json");
	}

	@Override
	public Identifier getTextureLocation(PlasmaGun object) {
		return new Identifier(DoomMod.MODID, "textures/items/rifle.png");
	}

	@Override
	public Identifier getAnimationFileLocation(PlasmaGun animatable) {
		return new Identifier(DoomMod.MODID, "animations/plasmagun.animation.json");
	}
}
