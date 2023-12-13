package mod.azure.doom.client.render.mobs.superheavy;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.doom.client.models.mobs.superheavy.DoomHunterModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tiersuperheavy.DoomHunterEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class DoomHunterRender extends DoomMobRender<DoomHunterEntity> {

    public DoomHunterRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new DoomHunterModel());
    }

    @Override
    public void preRender(PoseStack poseStack, DoomHunterEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight,
                packedOverlay, red, green, blue, alpha);
        if (animatable.getEntityData().get(DoomHunterEntity.DEATH_STATE) == 0)
            model.getBone("sled").get().setHidden(false);
        if (animatable.getEntityData().get(DoomHunterEntity.DEATH_STATE) == 1)
            model.getBone("sled").get().setHidden(true);
        if (animatable.getHealth() < 0.01 || animatable.isDeadOrDying())
            model.getBone("sled").get().setHidden(true);
    }

}