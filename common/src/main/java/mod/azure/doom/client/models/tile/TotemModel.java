package mod.azure.doom.client.models.tile;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.blocks.blockentities.TotemEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class TotemModel extends GeoModel<TotemEntity> {
    @Override
    public ResourceLocation getAnimationResource(TotemEntity entity) {
        return MCDoom.modResource("animations/totem.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(TotemEntity animatable) {
        return MCDoom.modResource("geo/totem.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TotemEntity entity) {
        return MCDoom.modResource("textures/block/totem.png");
    }

    @Override
    public RenderType getRenderType(TotemEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
