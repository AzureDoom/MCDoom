package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.HeavyCannon;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class HeavyCannonModel extends GeoModel<HeavyCannon> {
	@Override
	public ResourceLocation getModelResource(HeavyCannon object) {
		return DoomMod.modResource("geo/heavycannon.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(HeavyCannon object) {
		return DoomMod.modResource("textures/item/heavycannon.png");
	}

	@Override
	public ResourceLocation getAnimationResource(HeavyCannon animatable) {
		return DoomMod.modResource("animations/heavycannon.animation.json");
	}
}
