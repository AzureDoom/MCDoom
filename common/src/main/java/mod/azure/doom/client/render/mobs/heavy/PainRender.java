package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.doom.client.models.mobs.heavy.PainModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierheavy.PainEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class PainRender extends DoomMobRender<PainEntity> {

    public PainRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new PainModel());
    }

}