package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.heavy.CacodemonModel;
import mod.azure.doom.entities.tierheavy.CacodemonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CacodemonRender extends GeoEntityRenderer<CacodemonEntity> {

    public CacodemonRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new CacodemonModel());
    }

    @Override
    protected float getDeathMaxRotation(CacodemonEntity entityLivingBaseIn) {
        return 0.0F;
    }

}