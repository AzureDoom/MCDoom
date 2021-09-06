package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.PlasmaGun;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PlasmagunModel extends AnimatedGeoModel<PlasmaGun> {
	@Override
	public ResourceLocation getModelLocation(PlasmaGun object) {
		return new ResourceLocation(DoomMod.MODID, "geo/plasmagun.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(PlasmaGun object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/rifle.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(PlasmaGun animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/plasmagun.animation.json");
	}
}
