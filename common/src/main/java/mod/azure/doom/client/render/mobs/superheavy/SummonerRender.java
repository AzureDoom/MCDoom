package mod.azure.doom.client.render.mobs.superheavy;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.superheavy.SummonerModel;
import mod.azure.doom.entities.tiersuperheavy.SummonerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SummonerRender extends GeoEntityRenderer<SummonerEntity> {

    public SummonerRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new SummonerModel());
    }

    @Override
    protected float getDeathMaxRotation(SummonerEntity entityLivingBaseIn) {
        return 0.0F;
    }

}