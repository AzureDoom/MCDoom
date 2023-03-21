package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Unmaker;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class UnmakerModel extends GeoModel<Unmaker> {
	@Override
	public ResourceLocation getModelResource(Unmaker object) {
		return DoomMod.modResource("geo/unmaykr.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Unmaker object) {
		return DoomMod.modResource("textures/item/unmaker.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Unmaker animatable) {
		return DoomMod.modResource("animations/unmaykr.animation.json");
	}
}
