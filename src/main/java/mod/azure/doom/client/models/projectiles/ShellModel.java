package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShellModel extends AnimatedGeoModel<ShotgunShellEntity> {
	@Override
	public ResourceLocation getModelLocation(ShotgunShellEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/shell.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ShotgunShellEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/items/shell.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ShotgunShellEntity animatable) {
		return new ResourceLocation(DoomMod.MODID, "animations/empty.animation.json");
	}
}
