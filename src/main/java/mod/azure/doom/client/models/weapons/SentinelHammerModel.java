package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.SentinelHammerItem;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SentinelHammerModel extends AnimatedGeoModel<SentinelHammerItem> {
	@Override
	public ResourceLocation getModelLocation(SentinelHammerItem object) {
		return new ResourceLocation(DoomMod.MODID, "geo/sentinelhammer.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SentinelHammerItem object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/sentinel_hammer.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(SentinelHammerItem animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/sentinelhammer.animation.json");
	}
}
