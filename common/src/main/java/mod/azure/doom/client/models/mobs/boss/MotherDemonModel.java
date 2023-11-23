package mod.azure.doom.client.models.mobs.boss;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierboss.MotherDemonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class MotherDemonModel extends GeoModel<MotherDemonEntity> {

    @Override
    public ResourceLocation getModelResource(MotherDemonEntity object) {
        return MCDoom.modResource("geo/motherdemon.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MotherDemonEntity object) {
        return MCDoom.modResource("textures/entity/motherdemon.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MotherDemonEntity object) {
        return MCDoom.modResource("animations/motherdemon.animation.json");
    }

    @Override
    public RenderType getRenderType(MotherDemonEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}