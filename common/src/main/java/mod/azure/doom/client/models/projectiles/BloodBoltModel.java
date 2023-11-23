package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.projectiles.entity.BloodBoltEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class BloodBoltModel extends GeoModel<BloodBoltEntity> {
    @Override
    public ResourceLocation getModelResource(BloodBoltEntity object) {
        return MCDoom.modResource("geo/bloodbolt.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BloodBoltEntity object) {
        return MCDoom.modResource("textures/entity/projectiles/bloodbolt.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BloodBoltEntity animatable) {
        return MCDoom.modResource("animations/bloodbolt.animation.json");
    }

    @Override
    public RenderType getRenderType(BloodBoltEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
