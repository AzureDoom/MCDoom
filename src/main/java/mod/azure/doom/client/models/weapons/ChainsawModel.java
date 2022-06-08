package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.ChainsawAnimated;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChainsawModel extends AnimatedGeoModel<ChainsawAnimated> {
	@Override
	public ResourceLocation getModelResource(ChainsawAnimated object) {
		return new ResourceLocation(DoomMod.MODID, "geo/chainsaw_eternal.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ChainsawAnimated object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/chainsaweternal.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ChainsawAnimated animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/chainsaw.animation.json");
	}
}
