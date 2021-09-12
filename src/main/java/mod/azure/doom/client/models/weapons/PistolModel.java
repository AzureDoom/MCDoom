package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.PistolItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PistolModel extends AnimatedGeoModel<PistolItem> {
	@Override
	public ResourceLocation getModelLocation(PistolItem object) {
		return new ResourceLocation(DoomMod.MODID, "geo/pistol.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(PistolItem object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/pistol.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(PistolItem animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/pistol.animation.json");
	}
}
