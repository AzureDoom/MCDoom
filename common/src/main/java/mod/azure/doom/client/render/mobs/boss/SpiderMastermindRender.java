package mod.azure.doom.client.render.mobs.boss;

import mod.azure.doom.client.models.mobs.boss.SpiderMastermindModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierboss.SpiderMastermindEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SpiderMastermindRender extends DoomMobRender<SpiderMastermindEntity> {

    public SpiderMastermindRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new SpiderMastermindModel());
    }
}