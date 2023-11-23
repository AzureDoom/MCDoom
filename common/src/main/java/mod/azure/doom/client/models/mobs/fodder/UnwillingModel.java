package mod.azure.doom.client.models.mobs.fodder;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierfodder.UnwillingEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class UnwillingModel extends GeoModel<UnwillingEntity> {

    @Override
    public ResourceLocation getModelResource(UnwillingEntity object) {
        return MCDoom.modResource("geo/unwilling.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(UnwillingEntity object) {
        return MCDoom.modResource("textures/entity/unwilling.png");
    }

    @Override
    public ResourceLocation getAnimationResource(UnwillingEntity object) {
        return MCDoom.modResource("animations/possessed_scientist_animation.json");
    }

    @Override
    public void setCustomAnimations(UnwillingEntity animatable, long instanceId, AnimationState<UnwillingEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("Head");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Override
    public RenderType getRenderType(UnwillingEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }

}