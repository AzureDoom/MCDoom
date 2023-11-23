package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.projectiles.entity.RocketMobEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class RocketMobModel extends GeoModel<RocketMobEntity> {
    @Override
    public ResourceLocation getModelResource(RocketMobEntity object) {
        return MCDoom.modResource("geo/rocket.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RocketMobEntity object) {
        return MCDoom.modResource("textures/entity/projectiles/rocket.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RocketMobEntity animatable) {
        return MCDoom.modResource("animations/rocket.animation.json");
    }

    @Override
    public RenderType getRenderType(RocketMobEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
