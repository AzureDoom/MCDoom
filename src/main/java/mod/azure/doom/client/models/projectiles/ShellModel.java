package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShellModel extends AnimatedGeoModel<ShotgunShellEntity> {
	@Override
	public Identifier getModelResource(ShotgunShellEntity object) {
		return new Identifier(DoomMod.MODID, "geo/shell.geo.json");
	}

	@Override
	public Identifier getTextureResource(ShotgunShellEntity object) {
		return new Identifier(DoomMod.MODID, "textures/items/shell.png");
	}

	@Override
	public Identifier getAnimationResource(ShotgunShellEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/empty.animation.json");
	}
}
