package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.heavy.PinkyModel;
import mod.azure.doom.entities.tierheavy.PinkyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class PinkyRender extends GeoEntityRenderer<PinkyEntity> {

    public PinkyRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new PinkyModel());
    }

    @Override
    protected float getDeathMaxRotation(PinkyEntity entityLivingBaseIn) {
        return 0.0F;
    }

}