package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.SummonerEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SummonerModel extends AnimatedGeoModel<SummonerEntity> {

	@Override
	public Identifier getModelLocation(SummonerEntity object) {
		return new Identifier(DoomMod.MODID, "geo/summoner.geo.json");
	}

	@Override
	public Identifier getTextureLocation(SummonerEntity object) {
		return new Identifier(DoomMod.MODID,
				"textures/entity/" + (object.getVariant() == 2 ? "summoner_red" : "summoner") + ".png");
	}

	@Override
	public Identifier getAnimationFileLocation(SummonerEntity object) {
		return new Identifier(DoomMod.MODID, "animations/summoner.animation.json");
	}

}