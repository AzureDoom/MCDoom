package mod.azure.doom.client.models.mobs.superheavy;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tiersuperheavy.BaronEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BaronModel extends GeoModel<BaronEntity> {

    @Override
    public ResourceLocation getModelResource(BaronEntity object) {
        return MCDoom.modResource("geo/baron.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BaronEntity object) {
        return MCDoom.modResource("textures/entity/baronofhell-" + (object.getVariant() == 2 ? "green" : object.getVariant() == 3 ? "64" : "texturemap") + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(BaronEntity object) {
        return MCDoom.modResource("animations/baron_hell_animation.json");
    }

    @Override
    public void setCustomAnimations(BaronEntity animatable, long instanceId, AnimationState<BaronEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("head");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Override
    public RenderType getRenderType(BaronEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}