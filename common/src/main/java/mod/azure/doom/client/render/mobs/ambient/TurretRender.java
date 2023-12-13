package mod.azure.doom.client.render.mobs.ambient;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.ambient.TurretModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierambient.TurretEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class TurretRender extends DoomMobRender<TurretEntity> {

    public TurretRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new TurretModel());
    }

}