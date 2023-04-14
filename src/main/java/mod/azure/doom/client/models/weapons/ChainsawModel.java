package mod.azure.doom.client.models.weapons;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.ChainsawAnimated;
import net.minecraft.resources.ResourceLocation;

public class ChainsawModel extends GeoModel<ChainsawAnimated> {
	@Override
	public ResourceLocation getModelResource(ChainsawAnimated object) {
		return new ResourceLocation(DoomMod.MODID, "geo/chainsaw_eternal.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ChainsawAnimated object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/chainsaweternal.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ChainsawAnimated animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/chainsaw.animation.json");
	}
}
