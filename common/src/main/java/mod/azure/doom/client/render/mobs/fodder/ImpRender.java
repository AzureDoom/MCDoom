package mod.azure.doom.client.render.mobs.fodder;

import mod.azure.doom.client.models.mobs.fodder.ImpModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierfodder.ImpEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ImpRender extends DoomMobRender<ImpEntity> {

    public ImpRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ImpModel());
    }

}