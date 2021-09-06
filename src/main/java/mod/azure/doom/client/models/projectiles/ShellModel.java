package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShellModel extends AnimatedGeoModel<ShotgunShellEntity> {
	@Override
	public Identifier getModelLocation(ShotgunShellEntity object) {
		return new Identifier(DoomMod.MODID, "geo/shell.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ShotgunShellEntity object) {
		return new Identifier(DoomMod.MODID, "textures/items/shell.png");
	}

	@Override
	public Identifier getAnimationFileLocation(ShotgunShellEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/empty.animation.json");
	}
}
