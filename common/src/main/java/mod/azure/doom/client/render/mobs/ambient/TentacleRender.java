package mod.azure.doom.client.render.mobs.ambient;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.ambient.TentacleModel;
import mod.azure.doom.entities.tierambient.TentacleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class TentacleRender extends GeoEntityRenderer<TentacleEntity> {

    public TentacleRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new TentacleModel());
    }

    @Override
    protected float getDeathMaxRotation(TentacleEntity entityLivingBaseIn) {
        return 0.0F;
    }

}