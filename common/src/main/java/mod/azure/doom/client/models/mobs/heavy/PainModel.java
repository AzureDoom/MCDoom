package mod.azure.doom.client.models.mobs.heavy;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierheavy.PainEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PainModel extends GeoModel<PainEntity> {

    @Override
    public ResourceLocation getModelResource(PainEntity object) {
        return new ResourceLocation(MCDoom.MOD_ID, "geo/" + (object.getVariant() == 2 ? "pain64" : object.getVariant() == 3 ? "paineternal" : "pain") + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PainEntity object) {
        return MCDoom.modResource("textures/entity/" + (object.getVariant() == 2 ? (object.getAttckingState() == 2 ? "painelemental64-attacking" : "painelemental64-normal") : object.getVariant() == 3 ? "paineternal" : (object.getAttckingState() == 1 ? "painelemental-attacking" : "painelemental-normal")) + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(PainEntity object) {
        return new ResourceLocation(MCDoom.MOD_ID, "animations/" + (object.getVariant() == 3 ? "paineternal." : "pain_") + "animation.json");
    }

    @Override
    public void setCustomAnimations(PainEntity animatable, long instanceId, AnimationState<PainEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("body");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Override
    public RenderType getRenderType(PainEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}