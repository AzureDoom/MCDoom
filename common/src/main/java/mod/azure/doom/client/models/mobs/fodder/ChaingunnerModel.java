package mod.azure.doom.client.models.mobs.fodder;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierfodder.ChaingunnerEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ChaingunnerModel extends GeoModel<ChaingunnerEntity> {

    public ChaingunnerModel() {
    }

    @Override
    public ResourceLocation getModelResource(ChaingunnerEntity object) {
        return MCDoom.modResource("geo/shotgunzombie.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ChaingunnerEntity object) {
        return MCDoom.modResource("textures/entity/chaingunner.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ChaingunnerEntity object) {
        return MCDoom.modResource("animations/chaingunner.animation.json");
    }

    @Override
    public void setCustomAnimations(ChaingunnerEntity animatable, long instanceId, AnimationState<ChaingunnerEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("head");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Override
    public RenderType getRenderType(ChaingunnerEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}