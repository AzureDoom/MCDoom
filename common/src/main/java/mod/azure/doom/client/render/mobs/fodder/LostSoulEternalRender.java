package mod.azure.doom.client.render.mobs.fodder;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.fodder.LostSoulEternalModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierfodder.LostSoulEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;

public class LostSoulEternalRender extends DoomMobRender<LostSoulEntity> {

    public LostSoulEternalRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new LostSoulEternalModel());
    }
}