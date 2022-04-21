package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.WhiplashEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedTickingGeoModel;

public class WhiplashModel extends AnimatedTickingGeoModel<WhiplashEntity> {

	@Override
	public Identifier getModelResource(WhiplashEntity object) {
		return new Identifier(DoomMod.MODID, "geo/whiplash.geo.json");
	}

	@Override
	public Identifier getTextureResource(WhiplashEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/whiplash.png");
	}

	@Override
	public Identifier getAnimationResource(WhiplashEntity object) {
		return new Identifier(DoomMod.MODID, "animations/whiplash.animation.json");
	}

}