package mod.azure.doom.client.render.mobs.fodder;

import mod.azure.doom.client.models.mobs.fodder.GargoyleModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierfodder.GargoyleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class GargoyleRender extends DoomMobRender<GargoyleEntity> {

    public GargoyleRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new GargoyleModel());
    }

}