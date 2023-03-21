package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Unmaykr;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class UnmaykrModel extends GeoModel<Unmaykr> {
	@Override
	public ResourceLocation getModelResource(Unmaykr object) {
		return DoomMod.modResource("geo/unmaykr.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Unmaykr object) {
		return DoomMod.modResource("textures/item/unmaykr.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Unmaykr animatable) {
		return DoomMod.modResource("animations/unmaykr.animation.json");
	}
}
