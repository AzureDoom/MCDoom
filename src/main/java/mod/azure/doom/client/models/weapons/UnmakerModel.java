package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Unmaykr;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UnmakerModel extends AnimatedGeoModel<Unmaykr> {
	@Override
	public ResourceLocation getModelResource(Unmaykr object) {
		return new ResourceLocation(DoomMod.MODID, "geo/unmaykr.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Unmaykr object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/unmaker.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Unmaykr animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/unmaykr.animation.json");
	}
}
