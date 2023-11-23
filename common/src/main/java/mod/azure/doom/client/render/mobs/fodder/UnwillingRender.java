package mod.azure.doom.client.render.mobs.fodder;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.fodder.UnwillingModel;
import mod.azure.doom.entities.tierfodder.UnwillingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class UnwillingRender extends GeoEntityRenderer<UnwillingEntity> {

    public UnwillingRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new UnwillingModel());
    }

    @Override
    protected float getDeathMaxRotation(UnwillingEntity entityLivingBaseIn) {
        return 0.0F;
    }

}