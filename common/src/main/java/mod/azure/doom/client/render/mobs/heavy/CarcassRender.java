package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.doom.client.models.mobs.heavy.CarcassModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierheavy.CarcassEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CarcassRender extends DoomMobRender<CarcassEntity> {

    public CarcassRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new CarcassModel());
        shadowRadius = 0.4F;
    }

}