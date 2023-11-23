package mod.azure.doom.client.models.mobs.boss;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierboss.SpiderMastermindEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class SpiderMastermindModel extends GeoModel<SpiderMastermindEntity> {

    @Override
    public ResourceLocation getModelResource(SpiderMastermindEntity object) {
        return MCDoom.modResource("geo/spidermastermind.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SpiderMastermindEntity object) {
        return MCDoom.modResource("textures/entity/spidermastermind-texturemap.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SpiderMastermindEntity object) {
        return MCDoom.modResource("animations/spidermastermind_animation.json");
    }

    @Override
    public RenderType getRenderType(SpiderMastermindEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}