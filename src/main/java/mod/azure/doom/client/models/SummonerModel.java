package mod.azure.doom.client.models;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.tiersuperheavy.SummonerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SummonerModel extends GeoModel<SummonerEntity> {

	@Override
	public ResourceLocation getModelResource(SummonerEntity object) {
		return new ResourceLocation(DoomMod.MODID, "geo/summoner.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(SummonerEntity object) {
		return new ResourceLocation(DoomMod.MODID,
				"textures/entity/" + (object.getVariant() == 2 ? "summoner_red" : "summoner") + ".png");
	}

	@Override
	public ResourceLocation getAnimationResource(SummonerEntity object) {
		return new ResourceLocation(DoomMod.MODID, "animations/summoner.animation.json");
	}

	@Override
	public RenderType getRenderType(SummonerEntity animatable, ResourceLocation texture) {
		return RenderType.entityTranslucent(getTextureResource(animatable));
	}

}