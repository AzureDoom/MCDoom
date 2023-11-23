package mod.azure.doom.client.render.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.util.RenderUtils;
import mod.azure.doom.MCDoom;
import mod.azure.doom.client.models.projectiles.MeatHookEntityModel;
import mod.azure.doom.entities.projectiles.MeatHookEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class MeatHookEntityRenderer extends GeoEntityRenderer<MeatHookEntity> {

    private static final RenderType CHAIN_LAYER = RenderType.entitySmoothCutout(MCDoom.modResource("textures/entity/chain.png"));

    public MeatHookEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new MeatHookEntityModel());
    }

    protected int getBlockLightLevel(MeatHookEntity entityIn, BlockPos partialTicks) {
        return 15;
    }

    @Override
    public void preRender(PoseStack poseStack, MeatHookEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        RenderUtils.faceRotation(poseStack, animatable, partialTick);
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void render(MeatHookEntity hookshot, float yaw, float tickDelta, PoseStack poseStack, MultiBufferSource provider, int light) {
        super.render(hookshot, yaw, tickDelta, poseStack, provider, light);
        if (hookshot.getOwner() instanceof Player player) {
            poseStack.pushPose();
            var bodyYawToRads = Math.toRadians(player.yBodyRot);
            var radius = MCDoom.config.enable_noncenter ? 0.8D : 0.0D;
            var startX = player.getX() + radius * Math.cos(bodyYawToRads);
            var startY = player.getY() + (player.getBbHeight() / 3D);
            var startZ = player.getZ() + radius * Math.sin(bodyYawToRads);
            var distanceX = (float) (startX - hookshot.getX());
            var distanceY = (float) (startY - hookshot.getY());
            var distanceZ = (float) (startZ - hookshot.getZ());

            renderChain(distanceX, distanceY, distanceZ, tickDelta, hookshot.tickCount, poseStack, provider, light);
            poseStack.popPose();
        }
    }

    public void renderChain(float x, float y, float z, float tickDelta, int tickCount, PoseStack poseStack, MultiBufferSource provider, int light) {
        var lengthXY = Mth.sqrt(x * x + z * z);
        var squaredLength = x * x + y * y + z * z;
        var length = Mth.sqrt(squaredLength);

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotation((float) (-Math.atan2(z, x)) - 1.5707964F));
        poseStack.mulPose(Axis.XP.rotation((float) (-Math.atan2(lengthXY, y)) - 1.5707964F));
        poseStack.mulPose(Axis.ZP.rotation(25));
        poseStack.pushPose();
        poseStack.translate(0.015, -0.2, 0);

        var vertexConsumer = provider.getBuffer(CHAIN_LAYER);
        var vertX1 = 0F;
        var vertY1 = 0.25F;
        var vertX2 = Mth.sin(6.2831855F) * 0.125F;
        var vertY2 = Mth.cos(6.2831855F) * 0.125F;
        var minU = 0F;
        var maxU = 0.1875F;
        var minV = 0.0F - ((float) tickCount + tickDelta) * 0.01F;
        var maxV = Mth.sqrt(squaredLength) / 8F - ((float) tickCount + tickDelta) * 0.01F;
        var entry = poseStack.last();
        var matrix4f = entry.pose();
        var matrix3f = entry.normal();

        vertexConsumer.vertex(matrix4f, vertX1, vertY1, 0F).color(0, 0, 0, 255).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, vertX1, vertY1, length).color(255, 255, 255, 255).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, vertX2, vertY2, length).color(255, 255, 255, 255).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, vertX2, vertY2, 0F).color(0, 0, 0, 255).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();

        poseStack.popPose();
        poseStack.mulPose(Axis.ZP.rotation(90));
        poseStack.translate(-0.015, -0.2, 0);

        entry = poseStack.last();
        matrix4f = entry.pose();
        matrix3f = entry.normal();

        vertexConsumer.vertex(matrix4f, vertX1, vertY1, 0F).color(0, 0, 0, 255).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, vertX1, vertY1, length).color(255, 255, 255, 255).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, vertX2, vertY2, length).color(255, 255, 255, 255).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, vertX2, vertY2, 0F).color(0, 0, 0, 255).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();

        poseStack.popPose();
    }
}