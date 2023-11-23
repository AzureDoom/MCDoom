package mod.azure.doom.client.models.mobs.superheavy;

import mod.azure.azurelib.constant.DataTickets;
import mod.azure.azurelib.core.animatable.model.CoreGeoBone;
import mod.azure.azurelib.core.animation.AnimationState;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.tiersuperheavy.ArchvileEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ArchvileModel extends GeoModel<ArchvileEntity> {

    private static final ResourceLocation[] TEX = {MCDoom.modResource("textures/entity/archvile_flame_1.png"), MCDoom.modResource("textures/entity/archvile_flame_2.png"), MCDoom.modResource("textures/entity/archvile_flame_3.png"), MCDoom.modResource("textures/entity/archvile_flame_4.png"), MCDoom.modResource("textures/entity/archvile_flame_5.png"), MCDoom.modResource("textures/entity/archvile_flame_6.png"), MCDoom.modResource("textures/entity/archvile_flame_7.png"),
            MCDoom.modResource("textures/entity/archvile_flame_8.png")};

    @Override
    public ResourceLocation getModelResource(ArchvileEntity object) {
        return new ResourceLocation(MCDoom.MOD_ID, "geo/" + (object.getVariant() == 1 ? "archvile" : "archvileeternal") + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ArchvileEntity object) {
        return (object.getVariant() == 1 ? (object.getAttckingState() == 1 ? TEX[(object.getFlameTimer())] : MCDoom.modResource("textures/entity/archvile.png")) : MCDoom.modResource("textures/entity/archvileeternal.png"));
    }

    @Override
    public ResourceLocation getAnimationResource(ArchvileEntity object) {
        return new ResourceLocation(MCDoom.MOD_ID, "animations/" + (object.getVariant() == 1 ? "archvile_" : "archvileeternal.") + "animation.json");
    }

    @Override
    public RenderType getRenderType(ArchvileEntity animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }

    @Override
    public void setCustomAnimations(ArchvileEntity animatable, long instanceId, AnimationState<ArchvileEntity> animationState) {
        CoreGeoBone body1 = getAnimationProcessor().getBone("thighs");
        CoreGeoBone body2 = getAnimationProcessor().getBone("root");

        if (animatable.getVariant() == 1)
            if (body1 != null)
                body1.setRotY(animationState.getData(DataTickets.ENTITY_MODEL_DATA).netHeadYaw() * Mth.DEG_TO_RAD);

        if (animatable.getVariant() == 2)
            if (body2 != null)
                body2.setRotY(animationState.getData(DataTickets.ENTITY_MODEL_DATA).netHeadYaw() * Mth.DEG_TO_RAD);

        super.setCustomAnimations(animatable, instanceId, animationState);
    }
}