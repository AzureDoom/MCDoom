package mod.azure.doom.client.render.mobs.superheavy;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.superheavy.CyberdemonModel;
import mod.azure.doom.entities.tiersuperheavy.CyberdemonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CyberdemonRender extends GeoEntityRenderer<CyberdemonEntity> {

    public CyberdemonRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new CyberdemonModel());
    }

    @Override
    protected float getDeathMaxRotation(CyberdemonEntity entityLivingBaseIn) {
        return 0.0F;
    }
}