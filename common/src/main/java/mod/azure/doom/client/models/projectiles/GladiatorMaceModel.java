package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.projectiles.entity.GladiatorMaceEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class GladiatorMaceModel extends GeoModel<GladiatorMaceEntity> {
    @Override
    public ResourceLocation getModelResource(GladiatorMaceEntity object) {
        return MCDoom.modResource("geo/gladiator_mace.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GladiatorMaceEntity object) {
        return MCDoom.modResource("textures/entity/gladiator.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GladiatorMaceEntity animatable) {
        return MCDoom.modResource("animations/gladiator_mace.animation.json");
    }

    @Override
    public RenderType getRenderType(GladiatorMaceEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
