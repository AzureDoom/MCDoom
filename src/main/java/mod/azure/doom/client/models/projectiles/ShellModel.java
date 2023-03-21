package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ShellModel extends GeoModel<ShotgunShellEntity> {
	@Override
	public ResourceLocation getModelResource(ShotgunShellEntity object) {
		return DoomMod.modResource("geo/shell.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ShotgunShellEntity object) {
		return DoomMod.modResource("textures/item/shell.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ShotgunShellEntity animatable) {
		return DoomMod.modResource("animations/empty.animation.json");
	}

	@Override
	public RenderType getRenderType(ShotgunShellEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}
}
