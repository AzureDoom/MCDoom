package mod.azure.doom.client.render.mobs.ambient;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.ambient.TurretModel;
import mod.azure.doom.entities.tierambient.TurretEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class TurretRender extends GeoEntityRenderer<TurretEntity> {

    public TurretRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new TurretModel());
    }

    @Override
    protected float getDeathMaxRotation(TurretEntity entityLivingBaseIn) {
        return 0.0F;
    }

}