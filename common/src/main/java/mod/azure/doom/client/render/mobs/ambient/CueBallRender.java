package mod.azure.doom.client.render.mobs.ambient;

import mod.azure.doom.client.models.mobs.ambient.CueballModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierambient.CueBallEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CueBallRender extends DoomMobRender<CueBallEntity> {

    public CueBallRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new CueballModel());
    }

    @Override
    public boolean shouldShowName(CueBallEntity entity) {
        return false;
    }
}