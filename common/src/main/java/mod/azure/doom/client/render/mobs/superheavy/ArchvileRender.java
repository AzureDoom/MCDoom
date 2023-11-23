package mod.azure.doom.client.render.mobs.superheavy;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.superheavy.ArchvileModel;
import mod.azure.doom.entities.tiersuperheavy.ArchvileEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;

public class ArchvileRender extends GeoEntityRenderer<ArchvileEntity> {

    public ArchvileRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ArchvileModel());
    }

    @Override
    protected int getBlockLightLevel(ArchvileEntity entity, BlockPos blockPos) {
        return entity.getAttckingState() == 1 ? 15 : 1;
    }

    @Override
    protected float getDeathMaxRotation(ArchvileEntity entityLivingBaseIn) {
        return 0.0F;
    }

}