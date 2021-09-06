package mod.azure.doom.client.models.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.ChainBladeEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ChainBladeModel extends AnimatedGeoModel<ChainBladeEntity> {
	@Override
	public Identifier getModelLocation(ChainBladeEntity object) {
		return new Identifier(DoomMod.MODID, "geo/chainblade.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ChainBladeEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/whiplash.png");
	}

	@Override
	public Identifier getAnimationFileLocation(ChainBladeEntity animatable) {
		return new Identifier(DoomMod.MODID, "animations/chainblade.animation.json");
	}
}
