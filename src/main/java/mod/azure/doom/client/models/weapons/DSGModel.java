package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DShotgun;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class DSGModel extends GeoModel<DShotgun> {
	@Override
	public ResourceLocation getModelResource(DShotgun object) {
		return DoomMod.modResource("geo/doomed_shotgun.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DShotgun object) {
		return DoomMod.modResource("textures/item/doomed_shotgun.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DShotgun animatable) {
		return DoomMod.modResource("animations/doomed_shotgun.animation.json");
	}
}
