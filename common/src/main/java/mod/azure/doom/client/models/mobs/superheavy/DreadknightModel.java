package mod.azure.doom.client.models.mobs.superheavy;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierheavy.Hellknight2016Entity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class DreadknightModel extends GeoModel<Hellknight2016Entity> {

    @Override
    public ResourceLocation getModelResource(Hellknight2016Entity object) {
        return MCDoom.modResource("geo/dreadknight.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Hellknight2016Entity object) {
        return MCDoom.modResource("textures/entity/dreadknight.png");
    }

    @Override
    public ResourceLocation getAnimationResource(Hellknight2016Entity object) {
        return MCDoom.modResource("animations/hellknight2016_animation.json");
    }

    @Override
    public void setCustomAnimations(Hellknight2016Entity animatable, long instanceId, AnimationState<Hellknight2016Entity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("neck");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX(entityData.headPitch() * ((float) Math.PI / 360F));
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 340F));
        }
    }

    @Override
    public RenderType getRenderType(Hellknight2016Entity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}