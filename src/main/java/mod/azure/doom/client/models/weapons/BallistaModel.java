package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.Ballista;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BallistaModel extends AnimatedGeoModel<Ballista> {
	@Override
	public ResourceLocation getModelLocation(Ballista object) {
		return new ResourceLocation(DoomMod.MODID, "geo/ballista.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(Ballista object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/ballista.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(Ballista animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/ballista.animation.json");
	}
}
