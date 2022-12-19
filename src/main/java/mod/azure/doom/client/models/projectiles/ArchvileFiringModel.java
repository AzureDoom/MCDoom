package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.DoomFireEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ArchvileFiringModel extends GeoModel<DoomFireEntity> {
	@Override
	public Identifier getModelResource(DoomFireEntity object) {
		return new Identifier(DoomMod.MODID, "geo/archvilefiring.geo.json");
	}

	@Override
	public Identifier getTextureResource(DoomFireEntity object) {
		return new Identifier(DoomMod.MODID, "textures/item/empty.png");
	}

	@Override
	public Identifier getAnimationResource(DoomFireEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/archvilefiring.animation.json");
	}

	@Override
	public RenderLayer getRenderType(DoomFireEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
