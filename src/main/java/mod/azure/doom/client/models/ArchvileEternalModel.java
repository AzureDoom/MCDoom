package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.ArchvileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class ArchvileEternalModel extends AnimatedTickingGeoModel<ArchvileEntity> {

	@Override
	public ResourceLocation getModelLocation(ArchvileEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/archvileeternal.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ArchvileEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/archvileeternal.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ArchvileEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/archvileeternal.animation.json");
	}
}