package mod.azure.doom.client.render.mobs.boss;

import mod.azure.doom.client.models.mobs.boss.GladiatorModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierboss.GladiatorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class GladiatorRender extends DoomMobRender<GladiatorEntity> {

    public GladiatorRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new GladiatorModel());
    }

}