package mod.azure.doom.client.render.mobs.boss;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.doom.client.models.mobs.boss.ArchMaykrModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierboss.ArchMakyrEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ArchMaykrRender extends DoomMobRender<ArchMakyrEntity> {

    public ArchMaykrRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new ArchMaykrModel());
    }

    @Override
    public void preRender(PoseStack poseStack, ArchMakyrEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight,
                packedOverlay, red, green, blue, alpha);
        if (animatable.getEntityData().get(ArchMakyrEntity.DEATH_STATE) == 5) {
            model.getBone("rWing4").get().setHidden(true);
            if (animatable.getVariant() == 1) {
                model.getBone("lArm2").get().setHidden(true);
                model.getBone("frontCloak").get().setHidden(true);
                model.getBone("leftCloak").get().setHidden(true);
                model.getBone("rightCloak").get().setHidden(true);
                model.getBone("backCloak").get().setHidden(true);
                model.getBone("lWing2").get().setHidden(true);
            }
            if (animatable.getVariant() == 2) {
                model.getBone("eye").get().setHidden(true);
                model.getBone("lWing1").get().setHidden(true);
                model.getBone("lWing4").get().setHidden(true);
            }
        }
        if (animatable.getEntityData().get(ArchMakyrEntity.DEATH_STATE) == 4) {
            model.getBone("rWing4").get().setHidden(true);
            if (animatable.getVariant() == 1) {
                model.getBone("lArm2").get().setHidden(true);
                model.getBone("frontCloak").get().setHidden(true);
                model.getBone("leftCloak").get().setHidden(true);
                model.getBone("rightCloak").get().setHidden(true);
                model.getBone("backCloak").get().setHidden(true);
                model.getBone("lWing2").get().setHidden(true);
            }
            if (animatable.getVariant() == 2) {
                model.getBone("eye").get().setHidden(true);
                model.getBone("lWing1").get().setHidden(true);
                model.getBone("lWing4").get().setHidden(true);
            }
        }
        if (animatable.getEntityData().get(ArchMakyrEntity.DEATH_STATE) == 3) {
            model.getBone("rWing4").get().setHidden(true);
            if (animatable.getVariant() == 1) {
                model.getBone("lArm2").get().setHidden(true);
                model.getBone("frontCloak").get().setHidden(true);
                model.getBone("leftCloak").get().setHidden(true);
                model.getBone("rightCloak").get().setHidden(true);
                model.getBone("backCloak").get().setHidden(true);
            }
            if (animatable.getVariant() == 2) {
                model.getBone("eye").get().setHidden(true);
                model.getBone("lWing4").get().setHidden(true);
            }
        }
        if (animatable.getEntityData().get(ArchMakyrEntity.DEATH_STATE) == 2) {
            model.getBone("rWing4").get().setHidden(true);
            if (animatable.getVariant() == 2) {
                model.getBone("lWing4").get().setHidden(true);
            }
            if (animatable.getVariant() == 1) {
                model.getBone("lArm2").get().setHidden(true);
            }
        }
        if (animatable.getEntityData().get(ArchMakyrEntity.DEATH_STATE) == 1) {
            model.getBone("rWing4").get().setHidden(true);
        }
        if (animatable.getEntityData().get(ArchMakyrEntity.DEATH_STATE) == 0) {
            model.getBone("rWing4").get().setHidden(false);
            if (animatable.getVariant() == 1) {
                model.getBone("frontCloak").get().setHidden(false);
                model.getBone("leftCloak").get().setHidden(false);
                model.getBone("rightCloak").get().setHidden(false);
                model.getBone("backCloak").get().setHidden(false);
                model.getBone("lWing2").get().setHidden(false);
                model.getBone("lArm2").get().setHidden(false);
            }
            if (animatable.getVariant() == 2) {
                model.getBone("eye").get().setHidden(false);
                model.getBone("lWing1").get().setHidden(false);
                model.getBone("lWing4").get().setHidden(false);
            }
        }
    }
}