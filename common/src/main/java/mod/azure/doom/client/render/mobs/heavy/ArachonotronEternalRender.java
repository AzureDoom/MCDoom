package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.heavy.ArachonotronEternalModel;
import mod.azure.doom.entities.tierheavy.ArachnotronEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ArachonotronEternalRender extends GeoEntityRenderer<ArachnotronEntity> {

    public ArachonotronEternalRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ArachonotronEternalModel());
        shadowRadius = 0.7F;
    }

    @Override
    protected float getDeathMaxRotation(ArachnotronEntity entityLivingBaseIn) {
        return 0.0F;
    }

}