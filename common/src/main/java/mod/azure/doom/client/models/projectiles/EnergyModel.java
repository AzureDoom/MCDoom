package mod.azure.doom.client.models.projectiles;

import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.projectiles.EnergyCellEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class EnergyModel extends GeoModel<EnergyCellEntity> {
    @Override
    public ResourceLocation getModelResource(EnergyCellEntity object) {
        return MCDoom.modResource("geo/smallprojectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EnergyCellEntity object) {
        return MCDoom.modResource("textures/entity/projectiles/plasma_ball.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EnergyCellEntity animatable) {
        return MCDoom.modResource("animations/smallprojectile.animation.json");
    }

    @Override
    public RenderType getRenderType(EnergyCellEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}
