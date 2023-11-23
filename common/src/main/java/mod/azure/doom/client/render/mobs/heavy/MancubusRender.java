package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.heavy.MancubusModel;
import mod.azure.doom.entities.tierheavy.MancubusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class MancubusRender extends GeoEntityRenderer<MancubusEntity> {

    public MancubusRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new MancubusModel());
    }

    @Override
    protected float getDeathMaxRotation(MancubusEntity entityLivingBaseIn) {
        return 0.0F;
    }

}