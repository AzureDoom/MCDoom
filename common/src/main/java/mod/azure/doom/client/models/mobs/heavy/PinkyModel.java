package mod.azure.doom.client.models.mobs.heavy;

import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierheavy.PinkyEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class PinkyModel extends GeoModel<PinkyEntity> {

    @Override
    public ResourceLocation getModelResource(PinkyEntity object) {
        return MCDoom.modResource("geo/" + (object.getVariant() == 3 ? "pinky2016" : "pinky") + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PinkyEntity object) {
        return MCDoom.modResource("textures/entity/" + (object.getVariant() == 4 ? "pinky_green" : object.getVariant() == 2 ? "pinky-64" : object.getVariant() == 3 ? "pinky2016" : "pinky-texturemap") + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(PinkyEntity object) {
        return new ResourceLocation(MCDoom.MOD_ID, "animations/" + (object.getVariant() == 3 ? "pinky2016." : "pinky_") + "animation.json");
    }

    @Override
    public void setCustomAnimations(PinkyEntity animatable, long instanceId, AnimationState<PinkyEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
    }

    @Override
    public RenderType getRenderType(PinkyEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}