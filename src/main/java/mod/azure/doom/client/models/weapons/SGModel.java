package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Shotgun;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SGModel extends AnimatedGeoModel<Shotgun> {
	@Override
	public ResourceLocation getModelResource(Shotgun object) {
		return new ResourceLocation(DoomMod.MODID, "geo/shotgun.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Shotgun object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/shotgun.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Shotgun animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/shotgun.animation.json");
	}
}
