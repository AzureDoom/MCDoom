package mod.azure.doom.client.render.mobs.superheavy;

import mod.azure.doom.client.models.mobs.superheavy.CyberdemonModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tiersuperheavy.CyberdemonEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CyberdemonRender extends DoomMobRender<CyberdemonEntity> {

    public CyberdemonRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new CyberdemonModel());
    }
}