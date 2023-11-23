package mod.azure.doom.client.models.mobs.superheavy;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tiersuperheavy.CyberdemonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class CyberdemonModel extends GeoModel<CyberdemonEntity> {

    public ResourceLocation classic_model = MCDoom.modResource("geo/cyberdemon.geo.json");
    public ResourceLocation c2016_model = MCDoom.modResource("geo/cyberdemon2016.geo.json");
    public ResourceLocation tyrant_model = MCDoom.modResource("geo/tyrant.geo.json");
    public ResourceLocation classic_texture = MCDoom.modResource("textures/entity/cyberdemon-texturemap.png");
    public ResourceLocation d64_texture = MCDoom.modResource("textures/entity/cyberdemon-64.png");
    public ResourceLocation c2016_texture = MCDoom.modResource("textures/entity/cyberdemon2016.png");
    public ResourceLocation tyrant_texture = MCDoom.modResource("textures/entity/tyrant.png");
    public ResourceLocation classic_animation = MCDoom.modResource("animations/cyberdemon_animation.json");
    public ResourceLocation c2016_animation = MCDoom.modResource("animations/cyberdemon2016.animation.json");
    public ResourceLocation tyrant_animation = MCDoom.modResource("animations/tyrant.animation.json");

    @Override
    public ResourceLocation getModelResource(CyberdemonEntity object) {
        return object.getVariant() == 2 ? c2016_model : object.getVariant() == 3 ? tyrant_model : classic_model;
    }

    @Override
    public ResourceLocation getTextureResource(CyberdemonEntity object) {
        return object.getVariant() == 2 ? c2016_texture
                : object.getVariant() == 3 ? tyrant_texture : object.getVariant() == 4 ? d64_texture : classic_texture;
    }

    @Override
    public ResourceLocation getAnimationResource(CyberdemonEntity object) {
        return object.getVariant() == 2 ? c2016_animation
                : object.getVariant() == 3 ? tyrant_animation : classic_animation;
    }

    @Override
    public RenderType getRenderType(CyberdemonEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}