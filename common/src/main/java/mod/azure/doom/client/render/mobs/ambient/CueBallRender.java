package mod.azure.doom.client.render.mobs.ambient;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.ambient.CueballModel;
import mod.azure.doom.entities.tierambient.CueBallEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;

public class CueBallRender extends GeoEntityRenderer<CueBallEntity> {

    public CueBallRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new CueballModel());
    }

    @Override
    protected int getBlockLightLevel(CueBallEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    protected float getDeathMaxRotation(CueBallEntity entityLivingBaseIn) {
        return 0.0F;
    }

    @Override
    public boolean shouldShowName(CueBallEntity entity) {
        return false;
    }
}