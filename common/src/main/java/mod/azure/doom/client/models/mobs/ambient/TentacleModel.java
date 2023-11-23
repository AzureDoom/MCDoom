package mod.azure.doom.client.models.mobs.ambient;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierambient.TentacleEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class TentacleModel extends GeoModel<TentacleEntity> {

    @Override
    public ResourceLocation getModelResource(TentacleEntity object) {
        return MCDoom.modResource("geo/tentacle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TentacleEntity object) {
        return MCDoom.modResource("textures/entity/tentacle.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TentacleEntity object) {
        return MCDoom.modResource("animations/tentacle.animation.json");
    }

    @Override
    public void setCustomAnimations(TentacleEntity animatable, long instanceId, AnimationState<TentacleEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("bone3");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX((entityData.headPitch() - 30) * ((float) Math.PI / 360F));
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 360F));
        }
    }

    @Override
    public RenderType getRenderType(TentacleEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}