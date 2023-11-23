package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.projectiles.BFGEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class BFGBallModel extends GeoModel<BFGEntity> {
    @Override
    public ResourceLocation getModelResource(BFGEntity object) {
        return MCDoom.modResource("geo/bfg_ball.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BFGEntity object) {
        return MCDoom.modResource("textures/entity/projectiles/bfg_ball.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BFGEntity animatable) {
        return MCDoom.modResource("animations/bfgball.animation.json");
    }

    @Override
    public RenderType getRenderType(BFGEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
