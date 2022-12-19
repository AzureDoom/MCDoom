package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShellModel extends GeoModel<ShotgunShellEntity> {
	@Override
	public Identifier getModelResource(ShotgunShellEntity object) {
		return new Identifier(DoomMod.MODID, "geo/shell.geo.json");
	}

	@Override
	public Identifier getTextureResource(ShotgunShellEntity object) {
		return new Identifier(DoomMod.MODID, "textures/item/shell.png");
	}

	@Override
	public Identifier getAnimationResource(ShotgunShellEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/empty.animation.json");
	}

	@Override
	public RenderLayer getRenderType(ShotgunShellEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
