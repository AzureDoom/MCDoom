package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Shotgun;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class SGModel extends GeoModel<Shotgun> {
	@Override
	public ResourceLocation getModelResource(Shotgun object) {
		return new ResourceLocation(DoomMod.MODID, "geo/shotgun.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Shotgun object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/shotgun.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Shotgun animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/shotgun.animation.json");
	}
}
