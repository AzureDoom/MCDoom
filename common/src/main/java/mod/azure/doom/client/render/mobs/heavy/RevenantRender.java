package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.doom.client.models.mobs.heavy.RevenantModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierheavy.RevenantEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class RevenantRender extends DoomMobRender<RevenantEntity> {

    public RevenantRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new RevenantModel());
    }

}