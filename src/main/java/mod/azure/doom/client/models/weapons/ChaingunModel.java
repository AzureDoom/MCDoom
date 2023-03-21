package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Chaingun;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class ChaingunModel extends GeoModel<Chaingun> {
	@Override
	public ResourceLocation getModelResource(Chaingun object) {
		return DoomMod.modResource("geo/chaingun.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Chaingun object) {
		return DoomMod.modResource("textures/item/chainguneternal.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Chaingun animatable) {
		return DoomMod.modResource("animations/chaingun.animation.json");
	}
}
