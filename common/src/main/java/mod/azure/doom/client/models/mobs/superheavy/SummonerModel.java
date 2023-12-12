package mod.azure.doom.client.models.mobs.superheavy;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tiersuperheavy.SummonerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class SummonerModel extends GeoModel<SummonerEntity> {

    @Override
    public ResourceLocation getModelResource(SummonerEntity object) {
        return MCDoom.modResource("geo/summoner.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SummonerEntity object) {
        return new ResourceLocation(MCDoom.MOD_ID,
                "textures/entity/" + (object.getVariant() == 2 ? "summoner_red" : "summoner") + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(SummonerEntity object) {
        return MCDoom.modResource("animations/summoner.animation.json");
    }

    @Override
    public RenderType getRenderType(SummonerEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }

}