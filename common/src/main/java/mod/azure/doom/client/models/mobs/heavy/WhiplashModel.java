package mod.azure.doom.client.models.mobs.heavy;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierheavy.WhiplashEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class WhiplashModel extends GeoModel<WhiplashEntity> {

    @Override
    public ResourceLocation getModelResource(WhiplashEntity object) {
        return MCDoom.modResource("geo/whiplash.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(WhiplashEntity object) {
        return MCDoom.modResource("textures/entity/whiplash.png");
    }

    @Override
    public ResourceLocation getAnimationResource(WhiplashEntity object) {
        return MCDoom.modResource("animations/whiplash.animation.json");
    }

    @Override
    public RenderType getRenderType(WhiplashEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }

}