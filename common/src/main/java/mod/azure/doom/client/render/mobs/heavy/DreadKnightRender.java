package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.doom.client.models.mobs.superheavy.DreadknightModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierheavy.Hellknight2016Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DreadKnightRender extends DoomMobRender<Hellknight2016Entity> {

    public DreadKnightRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new DreadknightModel());
    }

}