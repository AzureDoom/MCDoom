package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.SwordCrucibleItem;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class SwordCrucibleModel extends GeoModel<SwordCrucibleItem> {
	@Override
	public ResourceLocation getModelResource(SwordCrucibleItem object) {
		return DoomMod.modResource("geo/cruciblesword.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SwordCrucibleItem object) {
		return DoomMod.modResource("textures/item/crucible.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SwordCrucibleItem animatable) {
		return DoomMod.modResource("animations/cruciblesword.animation.json");
	}
}
