package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.doom.client.models.mobs.heavy.ArachnotronModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierheavy.ArachnotronEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ArachonotronRender extends DoomMobRender<ArachnotronEntity> {

    public ArachonotronRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ArachnotronModel());
        shadowRadius = 0.7F;
    }

}