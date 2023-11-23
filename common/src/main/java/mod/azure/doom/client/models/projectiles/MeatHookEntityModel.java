package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.projectiles.MeatHookEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class MeatHookEntityModel extends GeoModel<MeatHookEntity> {
    @Override
    public ResourceLocation getModelResource(MeatHookEntity object) {
        return MCDoom.modResource("geo/archvilefiring.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MeatHookEntity object) {
        return MCDoom.modResource("textures/item/supershotgun.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MeatHookEntity animatable) {
        return MCDoom.modResource("animations/chainblade.animation.json");
    }

    @Override
    public RenderType getRenderType(MeatHookEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
