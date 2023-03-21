package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DarkLordCrucibleItem;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class DarkLordCrucibleModel extends GeoModel<DarkLordCrucibleItem> {
	@Override
	public ResourceLocation getModelResource(DarkLordCrucibleItem object) {
		return DoomMod.modResource("geo/darklordcrucible.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DarkLordCrucibleItem object) {
		return DoomMod.modResource("textures/item/darklordcrucible.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DarkLordCrucibleItem animatable) {
		return DoomMod.modResource("animations/cruciblesword.animation.json");
	}
}
