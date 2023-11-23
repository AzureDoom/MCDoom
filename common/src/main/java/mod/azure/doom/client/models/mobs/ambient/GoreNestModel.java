package mod.azure.doom.client.models.mobs.ambient;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierambient.GoreNestEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class GoreNestModel extends GeoModel<GoreNestEntity> {

    @Override
    public ResourceLocation getModelResource(GoreNestEntity object) {
        return MCDoom.modResource("geo/gorenest.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GoreNestEntity object) {
        return MCDoom.modResource("textures/entity/gore_nest.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GoreNestEntity object) {
        return MCDoom.modResource("animations/gorenest_animation.json");
    }

    @Override
    public RenderType getRenderType(GoreNestEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}