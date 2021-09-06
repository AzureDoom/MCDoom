package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.SummonerEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SummonerModel extends AnimatedGeoModel<SummonerEntity> {

	@Override
	public ResourceLocation getModelLocation(SummonerEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/summoner.geo.json");
	}

	@Override
	public ResourceLocation getTextureLocation(SummonerEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"textures/entity/" + (object.getVariant() == 2 ? "summoner_red" : "summoner") + ".png");
	}

	@Override
	public ResourceLocation getAnimationFileLocation(SummonerEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/summoner.animation.json");
	}

}