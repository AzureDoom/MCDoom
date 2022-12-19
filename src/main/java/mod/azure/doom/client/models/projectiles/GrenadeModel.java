package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.GrenadeEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GrenadeModel extends GeoModel<GrenadeEntity> {
	@Override
	public Identifier getModelResource(GrenadeEntity object) {
		return new Identifier(DoomMod.MODID, "geo/doomed_grenade.geo.json");
	}

	@Override
	public Identifier getTextureResource(GrenadeEntity object) {
		return new Identifier(DoomMod.MODID, "textures/item/doomed_grenade.png");
	}

	@Override
	public Identifier getAnimationResource(GrenadeEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomed_grenade.animation.json");
	}

	@Override
	public RenderLayer getRenderType(GrenadeEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
