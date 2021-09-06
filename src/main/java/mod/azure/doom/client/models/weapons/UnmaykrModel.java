package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Unmaykr;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class UnmaykrModel extends AnimatedGeoModel<Unmaykr> {
	@Override
	public ResourceLocation getModelLocation(Unmaykr object) {
		return new ResourceLocation(DoomMod.MODID, "geo/unmaykr.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(Unmaykr object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/unmaykr.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(Unmaykr animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/unmaykr.animation.json");
	}
}
