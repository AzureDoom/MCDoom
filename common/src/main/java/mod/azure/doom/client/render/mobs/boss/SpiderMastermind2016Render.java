package mod.azure.doom.client.render.mobs.boss;

import mod.azure.doom.client.models.mobs.boss.SpiderMastermind2016Model;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierboss.SpiderMastermind2016Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SpiderMastermind2016Render extends DoomMobRender<SpiderMastermind2016Entity> {

    public SpiderMastermind2016Render(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new SpiderMastermind2016Model());
    }

}