package mod.azure.doom.client.render.mobs.superheavy;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.superheavy.ArmoredBaronModel;
import mod.azure.doom.entities.tiersuperheavy.ArmoredBaronEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ArmoredBaronRender extends GeoEntityRenderer<ArmoredBaronEntity> {

    public ArmoredBaronRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ArmoredBaronModel());
    }

    @Override
    protected float getDeathMaxRotation(ArmoredBaronEntity entityLivingBaseIn) {
        return 0.0F;
    }

}