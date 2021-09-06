package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tierheavy.ArachnotronEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/**
 * Arachnotron - Batpixxler
 */
public class ArachnotronModel extends AnimatedGeoModel<ArachnotronEntity> {

	public ArachnotronModel() {
	}

	@Override
	public ResourceLocation getModelLocation(ArachnotronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/arachnotron.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(ArachnotronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "textures/entity/arachnotron-texturemap.png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(ArachnotronEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/arachnotron_walking.json");
	}

}