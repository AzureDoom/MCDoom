package mod.azure.doom.client.models.mobs.heavy;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierheavy.BloodMaykrEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BloodMaykrModel extends GeoModel<BloodMaykrEntity> {

    @Override
    public ResourceLocation getModelResource(BloodMaykrEntity object) {
        return MCDoom.modResource("geo/bloodmaykr.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BloodMaykrEntity object) {
        return MCDoom.modResource("textures/entity/bloodmaykr.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BloodMaykrEntity object) {
        return MCDoom.modResource("animations/bloodmaykr.animation.json");
    }

    @Override
    public void setCustomAnimations(BloodMaykrEntity animatable, long instanceId, AnimationState<BloodMaykrEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("neck");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Override
    public RenderType getRenderType(BloodMaykrEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }

}