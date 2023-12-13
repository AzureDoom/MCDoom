package mod.azure.doom.client.render.mobs.superheavy;

import mod.azure.doom.client.models.mobs.superheavy.Baron2016Model;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tiersuperheavy.BaronEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class Baron2016Render extends DoomMobRender<BaronEntity> {

    public Baron2016Render(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new Baron2016Model());
    }

}