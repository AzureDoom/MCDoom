package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Chaingun;
import net.minecraft.resources.ResourceLocation;

public class ChaingunModel extends GeoModel<Chaingun> {
	@Override
	public ResourceLocation getModelResource(Chaingun object) {
		return new ResourceLocation(DoomMod.MODID, "geo/chaingun.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Chaingun object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/chainguneternal.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Chaingun animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/chaingun.animation.json");
	}
}
