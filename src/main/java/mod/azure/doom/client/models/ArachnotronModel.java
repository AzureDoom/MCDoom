package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.ArachnotronEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/**
 * Arachnotron - Batpixxler
 */
public class ArachnotronModel extends AnimatedGeoModel<ArachnotronEntity> {

	public ArachnotronModel() {
	}

	@Override
	public Identifier getModelLocation(ArachnotronEntity object) {
		return new Identifier(DoomMod.MODID, "geo/arachnotron.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ArachnotronEntity object) {
		return new Identifier(DoomMod.MODID, "textures/entity/arachnotron-texturemap.png");
	}

	@Override
	public Identifier getAnimationFileLocation(ArachnotronEntity object) {
		return new Identifier(DoomMod.MODID, "animations/arachnotron_walking.json");
	}
}