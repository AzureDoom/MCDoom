package mod.azure.doom.client.models.mobs.heavy;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierheavy.SpectreEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class SpectreModel extends GeoModel<SpectreEntity> {

    @Override
    public ResourceLocation getModelResource(SpectreEntity object) {
        return MCDoom.modResource("geo/" + (object.getVariant() > 1 ? "pinky" : "pinky2016") + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SpectreEntity object) {
        return MCDoom.modResource(
                "textures/entity/" + (object.getVariant() == 2 ? "pinky-texturemap" : object.getVariant() == 3 ? "pinky_green" : "pinky2016") + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(SpectreEntity object) {
        return new ResourceLocation(MCDoom.MOD_ID,
                "animations/" + (object.getVariant() > 1 ? "pinky_" : "pinky2016.") + "animation.json");
    }

    @Override
    public void setCustomAnimations(SpectreEntity animatable, long instanceId, AnimationState<SpectreEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("neck");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX(
                    (entityData.headPitch() + (animatable.getVariant() == 1 ? 90 : 30)) * ((float) Math.PI / 360F));
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 500F));
        }
    }

    @Override
    public RenderType getRenderType(SpectreEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}