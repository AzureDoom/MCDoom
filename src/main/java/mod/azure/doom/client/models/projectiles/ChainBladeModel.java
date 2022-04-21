package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.ChainBladeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedGeoModel;

public class ChainBladeModel extends AnimatedGeoModel<ChainBladeEntity> {
	@Override
	public Identifier getModelResource(ChainBladeEntity object) {
		return new Identifier(DoomMod.MODID, "geo/chainblade.geo.json");
	}

	@Override
	public Identifier getTextureResource(ChainBladeEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/whiplash.png");
	}

	@Override
	public Identifier getAnimationResource(ChainBladeEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/chainblade.animation.json");
	}
}
