package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.projectiles.entity.DoomFireEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ArchvileFiringModel extends GeoModel<DoomFireEntity> {
    @Override
    public ResourceLocation getModelResource(DoomFireEntity object) {
        return MCDoom.modResource("geo/archvilefiring.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DoomFireEntity object) {
        return MCDoom.modResource("textures/item/empty.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DoomFireEntity animatable) {
        return MCDoom.modResource("animations/archvilefiring.animation.json");
    }

    @Override
    public RenderType getRenderType(DoomFireEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
