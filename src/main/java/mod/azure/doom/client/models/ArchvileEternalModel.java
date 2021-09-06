package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.ArchvileEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ArchvileEternalModel extends AnimatedGeoModel<ArchvileEntity> {

	@Override
	public Identifier getModelLocation(ArchvileEntity object) {
		return new Identifier(DoomMod.MODID, "geo/archvileeternal.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ArchvileEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/archvileeternal.png");
	}

	@Override
	public Identifier getAnimationFileLocation(ArchvileEntity object) {
		return new Identifier(DoomMod.MODID, "animations/archvileeternal.animation.json");
	}
}