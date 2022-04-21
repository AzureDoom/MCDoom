package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.GrenadeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class GrenadeModel extends AnimatedGeoModel<GrenadeEntity> {
	@Override
	public Identifier getModelResource(GrenadeEntity object) {
		return new Identifier(DoomMod.MODID, "geo/doomed_grenade.geo.json");
	}

	@Override
	public Identifier getTextureResource(GrenadeEntity object) {
		return new Identifier(DoomMod.MODID, "textures/items/doomed_grenade.png");
	}

	@Override
	public Identifier getAnimationResource(GrenadeEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomed_grenade.animation.json");
	}
}
