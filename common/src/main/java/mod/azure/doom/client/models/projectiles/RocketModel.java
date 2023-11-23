package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.projectiles.RocketEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class RocketModel extends GeoModel<RocketEntity> {
    @Override
    public ResourceLocation getModelResource(RocketEntity object) {
        return MCDoom.modResource("geo/rocket.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RocketEntity object) {
        return MCDoom.modResource("textures/entity/projectiles/rocket.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RocketEntity animatable) {
        return MCDoom.modResource("animations/rocket.animation.json");
    }

    @Override
    public RenderType getRenderType(RocketEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
