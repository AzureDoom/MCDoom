package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.GladiatorMaceEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class GladiatorMaceModel extends AnimatedGeoModel<GladiatorMaceEntity> {
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
}
