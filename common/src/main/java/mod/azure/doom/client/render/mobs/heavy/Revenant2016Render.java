package mod.azure.doom.client.render.mobs.heavy;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.mobs.heavy.Revenant2016Model;
import mod.azure.doom.entities.tierheavy.Revenant2016Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class Revenant2016Render extends GeoEntityRenderer<Revenant2016Entity> {

    public Revenant2016Render(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new Revenant2016Model());
    }

    @Override
    protected float getDeathMaxRotation(Revenant2016Entity entityLivingBaseIn) {
        return 0.0F;
    }

}