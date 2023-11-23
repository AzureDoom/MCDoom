package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.heavy.Hellknight2016Model;
import mod.azure.doom.entities.tierheavy.Hellknight2016Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class Hellknight2016Render extends GeoEntityRenderer<Hellknight2016Entity> {

    public Hellknight2016Render(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new Hellknight2016Model());
    }

    @Override
    protected float getDeathMaxRotation(Hellknight2016Entity entityLivingBaseIn) {
        return 0.0F;
    }

}