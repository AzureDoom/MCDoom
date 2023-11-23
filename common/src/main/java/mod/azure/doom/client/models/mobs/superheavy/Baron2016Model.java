package mod.azure.doom.client.models.mobs.superheavy;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tiersuperheavy.BaronEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class Baron2016Model extends GeoModel<BaronEntity> {

    @Override
    public ResourceLocation getModelResource(BaronEntity object) {
        return MCDoom.modResource("geo/baron2016.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BaronEntity object) {
        return MCDoom.modResource("textures/entity/baron2016.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BaronEntity object) {
        return MCDoom.modResource("animations/baron2016.animation.json");
    }

    @Override
    public void setCustomAnimations(BaronEntity animatable, long instanceId, AnimationState<BaronEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("neck");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX((entityData.headPitch() + 20) * ((float) Math.PI / 360F));
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
        }
    }

    @Override
    public RenderType getRenderType(BaronEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}