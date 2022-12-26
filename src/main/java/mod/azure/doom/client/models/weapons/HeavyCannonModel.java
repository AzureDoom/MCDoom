package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.HeavyCannon;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HeavyCannonModel extends GeoModel<HeavyCannon> {
	@Override
	public ResourceLocation getModelResource(HeavyCannon object) {
		return new ResourceLocation(DoomMod.MODID, "geo/heavycannon.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HeavyCannon object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/heavycannon.png");
	}

	@Override
	public ResourceLocation getAnimationResource(HeavyCannon animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/heavycannon.animation.json");
	}
}
