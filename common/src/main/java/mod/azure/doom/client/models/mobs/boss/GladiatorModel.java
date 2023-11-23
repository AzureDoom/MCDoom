package mod.azure.doom.client.models.mobs.boss;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierboss.GladiatorEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class GladiatorModel extends GeoModel<GladiatorEntity> {

    @Override
    public ResourceLocation getModelResource(GladiatorEntity object) {
        return MCDoom.modResource("geo/gladiator.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GladiatorEntity object) {
        return MCDoom.modResource("textures/entity/" + (object.getTextureState() == 1 ? "gladiator_1" : object.getTextureState() == 2 ? "gladiator_2" : "gladiator") + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(GladiatorEntity object) {
        return MCDoom.modResource("animations/gladiator.animation.json");
    }

    @Override
    public void setCustomAnimations(GladiatorEntity animatable, long instanceId, AnimationState<GladiatorEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("neck");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null)
            head.setRotX(entityData.headPitch() * ((float) Math.PI / 340F));
    }

    @Override
    public RenderType getRenderType(GladiatorEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}