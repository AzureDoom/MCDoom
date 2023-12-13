package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.doom.client.models.mobs.heavy.ProwlerModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierheavy.ProwlerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ProwlerRender extends DoomMobRender<ProwlerEntity> {

    public ProwlerRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ProwlerModel());
    }

}