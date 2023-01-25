package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Ballista;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class BallistaModel extends GeoModel<Ballista> {
	@Override
	public ResourceLocation getModelResource(Ballista object) {
		return new ResourceLocation(DoomMod.MODID, "geo/ballista.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(Ballista object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/ballista.png");
	}

	@Override
	public ResourceLocation getAnimationResource(Ballista animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/ballista.animation.json");
	}
}
