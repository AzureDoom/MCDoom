package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.projectiles.entity.EnergyCellMobEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class EnergyMobModel extends GeoModel<EnergyCellMobEntity> {
    @Override
    public ResourceLocation getModelResource(EnergyCellMobEntity object) {
        return MCDoom.modResource("geo/smallprojectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EnergyCellMobEntity object) {
        return MCDoom.modResource("textures/entity/projectiles/plasma_ball.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EnergyCellMobEntity animatable) {
        return MCDoom.modResource("animations/smallprojectile.animation.json");
    }

    @Override
    public RenderType getRenderType(EnergyCellMobEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
