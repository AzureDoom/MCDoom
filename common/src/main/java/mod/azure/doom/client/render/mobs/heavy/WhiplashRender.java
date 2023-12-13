package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.doom.client.models.mobs.heavy.WhiplashModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierheavy.WhiplashEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class WhiplashRender extends DoomMobRender<WhiplashEntity> {

    public WhiplashRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new WhiplashModel());
    }

}