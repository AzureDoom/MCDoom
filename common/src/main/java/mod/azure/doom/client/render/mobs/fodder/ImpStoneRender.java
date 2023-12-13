package mod.azure.doom.client.render.mobs.fodder;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.fodder.ImpStoneModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierfodder.ImpStoneEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ImpStoneRender extends DoomMobRender<ImpStoneEntity> {

    public ImpStoneRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ImpStoneModel());
    }

}