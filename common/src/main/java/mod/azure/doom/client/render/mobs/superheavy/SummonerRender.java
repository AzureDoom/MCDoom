package mod.azure.doom.client.render.mobs.superheavy;

import mod.azure.doom.client.models.mobs.superheavy.SummonerModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tiersuperheavy.SummonerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SummonerRender extends DoomMobRender<SummonerEntity> {

    public SummonerRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new SummonerModel());
    }

}