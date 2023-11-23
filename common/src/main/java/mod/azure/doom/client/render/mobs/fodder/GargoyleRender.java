package mod.azure.doom.client.render.mobs.fodder;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.fodder.GargoyleModel;
import mod.azure.doom.entities.tierfodder.GargoyleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class GargoyleRender extends GeoEntityRenderer<GargoyleEntity> {

    public GargoyleRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new GargoyleModel());
    }

    @Override
    protected float getDeathMaxRotation(GargoyleEntity entityLivingBaseIn) {
        return 0.0F;
    }

}