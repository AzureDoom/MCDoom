package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.ArachnotronEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.model.AnimatedTickingGeoModel;

/**
 * Arachnotron - Batpixxler
 */
public class ArachnotronModel extends AnimatedTickingGeoModel<ArachnotronEntity> {

	public ArachnotronModel() {
	}

	@Override
	public Identifier getModelResource(ArachnotronEntity object) {
		return new Identifier(DoomMod.MODID, "geo/arachnotron.geo.json");
	}

	@Override
	public Identifier getTextureResource(ArachnotronEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/arachnotron-texturemap.png");
	}

	@Override
	public Identifier getAnimationResource(ArachnotronEntity object) {
		return new Identifier(DoomMod.MODID, "animations/arachnotron_walking.json");
	}
}