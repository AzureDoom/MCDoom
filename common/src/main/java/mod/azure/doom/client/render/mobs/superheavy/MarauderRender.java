package mod.azure.doom.client.render.mobs.superheavy;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import mod.azure.doom.client.models.mobs.superheavy.MarauderModel;
import mod.azure.doom.entities.tiersuperheavy.MarauderEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class MarauderRender extends GeoEntityRenderer<MarauderEntity> {

    public MarauderRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new MarauderModel());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    protected float getDeathMaxRotation(MarauderEntity entityLivingBaseIn) {
        return 0.0F;
    }

    @Override
    public float getMotionAnimThreshold(MarauderEntity animatable) {
        return 0.005f;
    }
}