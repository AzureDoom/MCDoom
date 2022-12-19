package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.SummonerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SummonerModel extends GeoModel<SummonerEntity> {

	@Override
	public Identifier getModelResource(SummonerEntity object) {
		return new Identifier(DoomMod.MODID, "geo/summoner.geo.json");
	}

	@Override
	public Identifier getTextureResource(SummonerEntity object) {
		return new Identifier(DoomMod.MODID,
				"textures/entity/" + (object.getVariant() == 2 ? "summoner_red" : "summoner") + ".png");
	}

	@Override
	public Identifier getAnimationResource(SummonerEntity object) {
		return new Identifier(DoomMod.MODID, "animations/summoner.animation.json");
	}

	@Override
	public RenderLayer getRenderType(SummonerEntity animatable, Identifier texture) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

}