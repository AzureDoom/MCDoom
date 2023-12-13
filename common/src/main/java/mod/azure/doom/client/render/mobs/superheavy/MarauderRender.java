package mod.azure.doom.client.render.mobs.superheavy;

import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import mod.azure.doom.client.models.mobs.superheavy.MarauderModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tiersuperheavy.MarauderEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class MarauderRender extends DoomMobRender<MarauderEntity> {

    public MarauderRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new MarauderModel());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}