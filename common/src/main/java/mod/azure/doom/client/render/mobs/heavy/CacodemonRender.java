package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.doom.client.models.mobs.heavy.CacodemonModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierheavy.CacodemonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CacodemonRender extends DoomMobRender<CacodemonEntity> {

    public CacodemonRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new CacodemonModel());
    }

}