package mod.azure.doom.client.render.mobs.ambient;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.ambient.GoreNestModel;
import mod.azure.doom.entities.tierambient.GoreNestEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class GoreNestRender extends GeoEntityRenderer<GoreNestEntity> {

    public GoreNestRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new GoreNestModel());
    }

    @Override
    protected float getDeathMaxRotation(GoreNestEntity entityLivingBaseIn) {
        return 0.0F;
    }
}