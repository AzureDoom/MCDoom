package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Chaingun;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChaingunModel extends AnimatedGeoModel<Chaingun> {
	@Override
	public ResourceLocation getModelLocation(Chaingun object) {
		return new ResourceLocation(DoomMod.MODID, "geo/chaingun.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(Chaingun object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/chainguneternal.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(Chaingun animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/chaingun.animation.json");
	}
}
