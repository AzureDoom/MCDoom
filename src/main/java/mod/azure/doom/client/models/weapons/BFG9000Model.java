package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.BFG9000;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BFG9000Model extends AnimatedGeoModel<BFG9000> {
	@Override
	public ResourceLocation getModelLocation(BFG9000 object) {
		return new ResourceLocation(DoomMod.MODID, "geo/bfg9000.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(BFG9000 object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/bfg9000.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(BFG9000 animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/bfg9000.animation.json");
	}
}
