package mod.azure.doom.client.models.mobs.heavy;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierheavy.RevenantEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RevenantModel extends GeoModel<RevenantEntity> {

    @Override
    public ResourceLocation getModelResource(RevenantEntity object) {
        return MCDoom.modResource("geo/revenant.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(RevenantEntity object) {
        return MCDoom.modResource("textures/entity/revenant_nojetpack.png");
    }

    @Override
    public ResourceLocation getAnimationResource(RevenantEntity object) {
        return MCDoom.modResource("animations/revenant.animation.json");
    }

    @Override
    public void setCustomAnimations(RevenantEntity animatable, long instanceId, AnimationState<RevenantEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("head");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Override
    public RenderType getRenderType(RevenantEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}