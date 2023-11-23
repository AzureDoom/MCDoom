package mod.azure.doom.client.models.mobs.boss;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierboss.ArchMakyrEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ArchMaykrModel extends GeoModel<ArchMakyrEntity> {

    @Override
    public ResourceLocation getModelResource(ArchMakyrEntity object) {
        return MCDoom.modResource("geo/archmaykr_" + object.getVariant() + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ArchMakyrEntity object) {
        return MCDoom.modResource("textures/entity/archmaykr_" + object.getVariant() + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(ArchMakyrEntity object) {
        return MCDoom.modResource("animations/archmaykr_" + object.getVariant() + ".animation.json");
    }

    @Override
    public void setCustomAnimations(ArchMakyrEntity animatable, long instanceId, AnimationState<ArchMakyrEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("neck");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Override
    public RenderType getRenderType(ArchMakyrEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }

}