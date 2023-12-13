package mod.azure.doom.client.render.mobs.superheavy;

import mod.azure.doom.client.models.mobs.superheavy.BaronModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tiersuperheavy.BaronEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BaronRender extends DoomMobRender<BaronEntity> {

    public BaronRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new BaronModel());
    }

}