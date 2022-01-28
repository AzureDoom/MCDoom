package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.GrenadeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GrenadeModel extends AnimatedGeoModel<GrenadeEntity> {
	@Override
	public Identifier getModelLocation(GrenadeEntity object) {
		return new Identifier(DoomMod.MODID, "geo/doomed_grenade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(GrenadeEntity object) {
		return new Identifier(DoomMod.MODID, "textures/items/doomed_grenade.png");
	}

	@Override
	public Identifier getAnimationFileLocation(GrenadeEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/doomed_grenade.animation.json");
	}
}
