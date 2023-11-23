package mod.azure.doom.client.render.mobs.fodder;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.fodder.PossessedSoldierModel;
import mod.azure.doom.entities.tierfodder.PossessedSoldierEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class PossessedSoldierRender extends GeoEntityRenderer<PossessedSoldierEntity> {

    public PossessedSoldierRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new PossessedSoldierModel());
    }

    @Override
    protected float getDeathMaxRotation(PossessedSoldierEntity entityLivingBaseIn) {
        return 0.0F;
    }
}