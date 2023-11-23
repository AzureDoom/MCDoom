package mod.azure.doom.client.render.mobs.fodder;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.fodder.MaykrDroneModel;
import mod.azure.doom.entities.tierfodder.MaykrDroneEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class MaykrDroneRender extends GeoEntityRenderer<MaykrDroneEntity> {

    public MaykrDroneRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new MaykrDroneModel());
    }

    protected float getLyingAngle(MaykrDroneEntity entityLivingBaseIn) {
        return 0.0F;
    }

}