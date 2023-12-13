package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.doom.client.models.mobs.heavy.HellknightModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierheavy.HellknightEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class HellknightRender extends DoomMobRender<HellknightEntity> {

    public HellknightRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new HellknightModel());
    }

}