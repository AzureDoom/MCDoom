package mod.azure.doom.client.models.mobs.fodder;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierfodder.GargoyleEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class GargoyleModel extends GeoModel<GargoyleEntity> {

    @Override
    public ResourceLocation getModelResource(GargoyleEntity object) {
        return MCDoom.modResource("geo/gargoyleimp.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GargoyleEntity object) {
        return MCDoom.modResource("textures/entity/gargoyleimp.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GargoyleEntity object) {
        return MCDoom.modResource("animations/gargoyleimp.animation.json");
    }

    @Override
    public void setCustomAnimations(GargoyleEntity animatable, long instanceId, AnimationState<GargoyleEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("head");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null)
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
    }

    @Override
    public RenderType getRenderType(GargoyleEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}