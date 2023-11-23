package mod.azure.doom.client.models.mobs.fodder;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierfodder.MaykrDroneEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class MaykrDroneModel extends GeoModel<MaykrDroneEntity> {

    @Override
    public ResourceLocation getModelResource(MaykrDroneEntity object) {
        return MCDoom.modResource("geo/maykrdrone.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MaykrDroneEntity object) {
        return MCDoom.modResource("textures/entity/maykrdrone_" + object.getVariant() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(MaykrDroneEntity object) {
        return MCDoom.modResource("animations/maykrdrone.animation.json");
    }

    @Override
    public RenderType getRenderType(MaykrDroneEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
