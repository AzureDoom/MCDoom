package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.doom.client.models.mobs.heavy.MancubusModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierheavy.MancubusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class MancubusRender extends DoomMobRender<MancubusEntity> {

    public MancubusRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new MancubusModel());
    }

}