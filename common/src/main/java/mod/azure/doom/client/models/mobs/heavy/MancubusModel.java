package mod.azure.doom.client.models.mobs.heavy;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierheavy.MancubusEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MancubusModel extends GeoModel<MancubusEntity> {

    public String classic = "mancubus";
    public String classiccyber = "cyber_mancubus";
    public String d64 = "mancubus64";
    public String d2016 = "mancubus2016";
    public String d2016cyber = "cybermancubus2016";

    @Override
    public ResourceLocation getModelResource(MancubusEntity object) {
        return MCDoom.modResource("geo/" + (object.getVariant() == 2 ? d64 : object.getVariant() == 3 ? d2016 : object.getVariant() == 4 ? classic : object.getVariant() == 5 ? d2016 : classic) + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MancubusEntity object) {
        return MCDoom.modResource("textures/entity/" + (object.getVariant() == 2 ? d64 : object.getVariant() == 3 ? d2016 : object.getVariant() == 4 ? classiccyber : object.getVariant() == 5 ? d2016cyber : classic) + ".png");
    }

    @Override
    public ResourceLocation getAnimationResource(MancubusEntity object) {
        return MCDoom.modResource("animations/mancubus_animation.json");
    }

    @Override
    public void setCustomAnimations(MancubusEntity animatable, long instanceId, AnimationState<MancubusEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("head");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Override
    public RenderType getRenderType(MancubusEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}