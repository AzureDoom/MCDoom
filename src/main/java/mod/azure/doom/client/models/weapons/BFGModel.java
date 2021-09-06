package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.BFG;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BFGModel extends AnimatedGeoModel<BFG> {
	@Override
	public ResourceLocation getModelLocation(BFG object) {
		return new ResourceLocation(DoomMod.MODID, "geo/bfgeternal.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(BFG object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/bfgeternal.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(BFG animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/bfg.animation.json");
	}
}
