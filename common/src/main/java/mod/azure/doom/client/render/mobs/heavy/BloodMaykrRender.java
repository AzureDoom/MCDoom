package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.doom.client.models.mobs.heavy.BloodMaykrModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierheavy.BloodMaykrEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BloodMaykrRender extends DoomMobRender<BloodMaykrEntity> {

    public BloodMaykrRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new BloodMaykrModel());
    }

}