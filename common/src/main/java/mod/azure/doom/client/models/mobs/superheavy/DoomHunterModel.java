package mod.azure.doom.client.models.mobs.superheavy;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tiersuperheavy.DoomHunterEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class DoomHunterModel extends GeoModel<DoomHunterEntity> {

    private static final ResourceLocation[] TEX = {MCDoom.modResource("textures/entity/doomhunter.png"), MCDoom.modResource("textures/entity/doomhunter_1.png"), MCDoom.modResource("textures/entity/doomhunter_2.png"), MCDoom.modResource("textures/entity/doomhunter_3.png"), MCDoom.modResource("textures/entity/doomhunter_4.png"), MCDoom.modResource("textures/entity/doomhunter_5.png"), MCDoom.modResource("textures/entity/doomhunter_6.png"),
            MCDoom.modResource("textures/entity/doomhunter_7.png")};

    @Override
    public ResourceLocation getModelResource(DoomHunterEntity object) {
        return MCDoom.modResource("geo/doomhunter.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DoomHunterEntity object) {
        return TEX[(object.getFlameTimer())];
    }

    @Override
    public ResourceLocation getAnimationResource(DoomHunterEntity object) {
        return MCDoom.modResource("animations/doomhunter.animation.json");
    }

    @Override
    public void setCustomAnimations(DoomHunterEntity animatable, long instanceId, AnimationState<DoomHunterEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);

        var head = getAnimationProcessor().getBone("neck");
        var entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

        if (head != null) {
            head.setRotX(entityData.headPitch() * ((float) Math.PI / 270F));
            head.setRotY(entityData.netHeadYaw() * ((float) Math.PI / 270F));
        }
    }

    @Override
    public RenderType getRenderType(DoomHunterEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }

}