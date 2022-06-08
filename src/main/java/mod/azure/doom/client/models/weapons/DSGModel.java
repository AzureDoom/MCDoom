package mod.azure.doom.client.models.weapons;

import mod.azure.doom.DoomMod;
import mod.azure.doom.item.weapons.DShotgun;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DSGModel extends AnimatedGeoModel<DShotgun> {
	@Override
	public ResourceLocation getModelResource(DShotgun object) {
		return new ResourceLocation(DoomMod.MODID, "geo/doomed_shotgun.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(DShotgun object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/doomed_shotgun.png");
	}

	@Override
	public ResourceLocation getAnimationResource(DShotgun animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/doomed_shotgun.animation.json");
	}
}
