package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.SuperShotgun;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SSGModel extends AnimatedGeoModel<SuperShotgun> {
	@Override
	public ResourceLocation getModelResource(SuperShotgun object) {
		return new ResourceLocation(DoomMod.MODID, "geo/supershotgun.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SuperShotgun object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/supershotgun.png");
	}

	@Override
	public ResourceLocation getAnimationResource(SuperShotgun animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/supershotgun.animation.json");
	}
}
