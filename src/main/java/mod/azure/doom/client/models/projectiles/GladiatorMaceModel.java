package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.GladiatorMaceEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GladiatorMaceModel extends GeoModel<GladiatorMaceEntity> {
	@Override
	public Identifier getModelResource(GladiatorMaceEntity object) {
		return new Identifier(DoomMod.MODID, "geo/gladiator_mace.geo.json");
	}

	@Override
	public Identifier getTextureResource(GladiatorMaceEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/gladiator.png");
	}

	@Override
	public Identifier getAnimationResource(GladiatorMaceEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/gladiator_mace.animation.json");
	}

	@Override
	public RenderLayer getRenderType(GladiatorMaceEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}
}
