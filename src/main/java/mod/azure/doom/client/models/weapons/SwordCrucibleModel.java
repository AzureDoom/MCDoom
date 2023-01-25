package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.SwordCrucibleItem;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class SwordCrucibleModel extends GeoModel<SwordCrucibleItem> {
	@Override
	public ResourceLocation getModelResource(SwordCrucibleItem object) {
		return new ResourceLocation(DoomMod.MODID, "geo/cruciblesword.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SwordCrucibleItem object) {
		return new ResourceLocation(DoomMod.MODID, "textures/item/crucible.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SwordCrucibleItem animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/cruciblesword.animation.json");
	}
}
