package mod.azure.doom.client.render.mobs.heavy;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.doom.client.models.mobs.heavy.SpectreModel;
import mod.azure.doom.client.render.mobs.DoomMobRender;
import mod.azure.doom.entities.tierheavy.SpectreEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SpectreRender extends DoomMobRender<SpectreEntity> {

    public SpectreRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new SpectreModel());
    }

    @Override
    public void actuallyRender(PoseStack poseStack, SpectreEntity animatable, BakedGeoModel model, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.actuallyRender(poseStack, animatable, model, renderType, bufferSource, buffer, isReRender, partialTick,
                packedLight, packedOverlay, red, green, blue, 0.1F);
    }
}