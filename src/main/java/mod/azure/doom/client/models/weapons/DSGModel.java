package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DShotgun;
import net.minecraft.resources.ResourceLocation;

public class DSGModel extends GeoModel<DShotgun> {
	@Override
	public ResourceLocation getModelResource(DShotgun object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomed_shotgun.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DShotgun object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/doomed_shotgun.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DShotgun animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/doomed_shotgun.animation.json");
	}
}
