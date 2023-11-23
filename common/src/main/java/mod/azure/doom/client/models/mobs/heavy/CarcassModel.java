package mod.azure.doom.client.models.mobs.heavy;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierheavy.CarcassEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CarcassModel extends GeoModel<CarcassEntity> {

    @Override
    public ResourceLocation getModelResource(CarcassEntity object) {
        return new ResourceLocation(MCDoom.MOD_ID, "geo/carcass.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CarcassEntity object) {
        return new ResourceLocation(MCDoom.MOD_ID, "textures/entity/carcass.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CarcassEntity object) {
        return MCDoom.modResource("animations/carcass.animation.json");
    }

    @Override
    public RenderType getRenderType(CarcassEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }

    @Override
    public void setCustomAnimations(CarcassEntity animatable, long instanceId, AnimationState<CarcassEntity> animationState) {
        var head = getAnimationProcessor().getBone("head");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

        super.setCustomAnimations(animatable, instanceId, animationState);
    }

}