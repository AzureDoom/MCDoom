package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.heavy.HellknightModel;
import mod.azure.doom.entities.tierheavy.HellknightEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class HellknightRender extends GeoEntityRenderer<HellknightEntity> {

    public HellknightRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new HellknightModel());
    }

    @Override
    protected float getDeathMaxRotation(HellknightEntity entityLivingBaseIn) {
        return 0.0F;
    }

}