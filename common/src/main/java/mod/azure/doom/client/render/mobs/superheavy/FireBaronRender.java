package mod.azure.doom.client.render.mobs.superheavy;

import mod.azure.doom.client.models.mobs.superheavy.FireBaronModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tiersuperheavy.FireBaronEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class FireBaronRender extends DoomMobRender<FireBaronEntity> {

    public FireBaronRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new FireBaronModel());
    }

}