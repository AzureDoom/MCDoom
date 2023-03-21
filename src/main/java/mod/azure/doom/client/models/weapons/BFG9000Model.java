package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.BFG9000;
import net.minecraft.resources.ResourceLocation;
import mod.azure.azurelib.model.GeoModel;

public class BFG9000Model extends GeoModel<BFG9000> {
	@Override
	public ResourceLocation getModelResource(BFG9000 object) {
		return DoomMod.modResource("geo/bfg9000.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BFG9000 object) {
		return DoomMod.modResource("textures/item/bfg9000.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BFG9000 animatable) {
		return DoomMod.modResource("animations/bfg9000.animation.json");
	}
}
