package mod.azure.doom.client.models.mobs.fodder;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tierfodder.LostSoulEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class LostSoulModel extends GeoModel<LostSoulEntity> {

    private static final ResourceLocation[] TEX = {MCDoom.modResource("textures/entity/lost_soul_fire_1.png"), MCDoom.modResource("textures/entity/lost_soul_fire_2.png"), MCDoom.modResource("textures/entity/lost_soul_fire_3.png"), MCDoom.modResource("textures/entity/lost_soul_fire_4.png"), MCDoom.modResource("textures/entity/lost_soul_fire_5.png"), MCDoom.modResource("textures/entity/lost_soul_fire_6.png"), MCDoom.modResource("textures/entity/lost_soul_fire_7.png"),
            MCDoom.modResource("textures/entity/lost_soul_fire_8.png")};

    private static final ResourceLocation[] TEX1 = {MCDoom.modResource("textures/entity/lost_soul_green_fire_1.png"), MCDoom.modResource("textures/entity/lost_soul_green_fire_2.png"), MCDoom.modResource("textures/entity/lost_soul_green_fire_3.png"), MCDoom.modResource("textures/entity/lost_soul_green_fire_4.png"), MCDoom.modResource("textures/entity/lost_soul_green_fire_5.png"), MCDoom.modResource("textures/entity/lost_soul_green_fire_6.png"),
            MCDoom.modResource("textures/entity/lost_soul_green_fire_7.png"), MCDoom.modResource("textures/entity/lost_soul_green_fire_8.png")};

    private static final ResourceLocation[] TEX64 = {MCDoom.modResource("textures/entity/lost_soul_64_fire_1.png"), MCDoom.modResource("textures/entity/lost_soul_64_fire_2.png"), MCDoom.modResource("textures/entity/lost_soul_64_fire_3.png"), MCDoom.modResource("textures/entity/lost_soul_64_fire_4.png"), MCDoom.modResource("textures/entity/lost_soul_64_fire_5.png"), MCDoom.modResource("textures/entity/lost_soul_64_fire_6.png"),
            MCDoom.modResource("textures/entity/lost_soul_64_fire_7.png"), MCDoom.modResource("textures/entity/lost_soul_64_fire_8.png")};

    @Override
    public ResourceLocation getModelResource(LostSoulEntity object) {
        return object.getVariant() == 3 ? MCDoom.modResource("geo/lostsoul64.geo.json") : MCDoom.modResource("geo/lostsoul.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(LostSoulEntity object) {
        return object.getVariant() == 2 ? TEX1[(object.getFlameTimer())] : object.getVariant() == 3 ? TEX64[(object.getFlameTimer())] : TEX[(object.getFlameTimer())];
    }

    @Override
    public ResourceLocation getAnimationResource(LostSoulEntity object) {
        return MCDoom.modResource("animations/lostsoul_animation.json");
    }

    @Override
    public void setCustomAnimations(LostSoulEntity animatable, long instanceId, AnimationState<LostSoulEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("head");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }

    @Override
    public RenderType getRenderType(LostSoulEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}