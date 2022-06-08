package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.PistolItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PistolModel extends AnimatedGeoModel<PistolItem> {
	@Override
	public ResourceLocation getModelResource(PistolItem object) {
		return new ResourceLocation(DoomMod.MODID, "geo/pistol.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(PistolItem object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/pistol.png");
	}

	@Override
	public ResourceLocation getAnimationResource(PistolItem animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/pistol.animation.json");
	}
}
