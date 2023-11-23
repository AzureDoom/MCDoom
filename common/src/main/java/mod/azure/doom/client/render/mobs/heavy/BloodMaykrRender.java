package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.heavy.BloodMaykrModel;
import mod.azure.doom.entities.tierheavy.BloodMaykrEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BloodMaykrRender extends GeoEntityRenderer<BloodMaykrEntity> {

    public BloodMaykrRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new BloodMaykrModel());
    }

    @Override
    protected float getDeathMaxRotation(BloodMaykrEntity entityLivingBaseIn) {
        return 0.0F;
    }

}