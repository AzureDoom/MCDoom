package mod.azure.doom.client.render.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.util.RenderUtils;
import mod.azure.doom.MCDoom;
import mod.azure.doom.client.models.projectiles.BFGBallModel;
import mod.azure.doom.entities.projectiles.BFGEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class BFGCellRender extends GeoEntityRenderer<BFGEntity> {

    private static final RenderType CRYSTAL_BEAM_LAYER = RenderType.entityTranslucent(
            MCDoom.modResource("textures/entity/projectiles/bfg_beam.png"));

    public BFGCellRender(EntityRendererProvider.Context renderManagerIn) {
        super(renderManagerIn, new BFGBallModel());
    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, float x, float y, float z, float u, float v) {
        vertexConsumer.vertex(positionMatrix, x, y, z).color(255, 255, 255, 255).uv(u, v).overlayCoords(
                OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0.0f, -1.0f,
                0.0f).endVertex();
    }

    @Override
    protected int getBlockLightLevel(@NotNull BFGEntity entityIn, @NotNull BlockPos partialTicks) {
        return 15;
    }

    @Override
    public boolean shouldRender(@NotNull BFGEntity entity, @NotNull Frustum frustum, double x, double y, double z) {
        LivingEntity livingEntity;
        if (super.shouldRender(entity, frustum, x, y, z)) return true;
        if (entity.hasTargetedEntity() && (livingEntity = entity.getTargetedEntity()) != null) {
            var vec3 = this.fromLerpedPosition(livingEntity, livingEntity.getBbHeight() * 0.5, 1.0f);
            var vec31 = this.fromLerpedPosition(entity, entity.getEyeHeight(), 1.0f);
            return frustum.isVisible(new AABB(vec31.x, vec31.y, vec31.z, vec3.x, vec3.y, vec3.z));
        }
        return false;
    }

    private Vec3 fromLerpedPosition(Entity entity, double yOffset, float delta) {
        return new Vec3(Mth.lerp(delta, entity.xOld, entity.getX()),
                Mth.lerp(delta, entity.yOld, entity.getY()) + yOffset, Mth.lerp(delta, entity.zOld, entity.getZ()));
    }

    @Override
    public void render(@NotNull BFGEntity entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        var livingEntity = entity.getTargetedEntity();
        if (livingEntity != null) {
            poseStack.pushPose();
            poseStack.translate(0.0f, entity.getEyeHeight(), 0.0f);
            var vec3 = this.fromLerpedPosition(livingEntity, livingEntity.getBbHeight() * 0.5, partialTick).subtract(
                    this.fromLerpedPosition(entity, entity.getEyeHeight(), partialTick));
            vec3 = vec3.normalize();
            poseStack.mulPose(Axis.YP.rotationDegrees((float) (1.5707964f - Math.atan2(vec3.z, vec3.x)) * 57.295776f));
            poseStack.mulPose(Axis.XP.rotationDegrees((float) Math.acos(vec3.y) * 57.295776f));
            var aa = Mth.sin(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + 0.7853982f) * 0.282f;
            var ab = Mth.cos(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + 3.926991f) * 0.282f;
            var ac = Mth.sin(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + 3.926991f) * 0.282f;
            var ad = Mth.cos(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + 5.4977875f) * 0.282f;
            var ae = Mth.sin(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + 5.4977875f) * 0.282f;
            var af = Mth.cos(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + (float) Math.PI) * 0.2f;
            var ag = Mth.sin(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + (float) Math.PI) * 0.2f;
            var ah = Mth.cos(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + 0.0f) * 0.2f;
            var ai = Mth.sin(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + 0.0f) * 0.2f;
            var aj = Mth.cos(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + 1.5707964f) * 0.2f;
            var ak = Mth.sin(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + 1.5707964f) * 0.2f;
            var al = Mth.cos(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + 4.712389f) * 0.2f;
            var am = Mth.sin(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + 4.712389f) * 0.2f;
            var an = (vec3.length() + 1.0);
            var aq = -1.0f + entity.level().getGameTime() + partialTick * 0.5f % 1.0f;
            var ar = (vec3.length() + 1.0) * 2.5f + aq;
            var vertexConsumer = bufferSource.getBuffer(CRYSTAL_BEAM_LAYER);
            var entry = poseStack.last();
            var matrix4f = entry.pose();
            var matrix3f = entry.normal();
            BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, af, (float) an, ag, 0.4999f, (float) ar);
            BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, af, 0.0f, ag, 0.4999f, aq);
            BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ah, 0.0f, ai, 0.0f, aq);
            BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ah, (float) an, ai, 0.0f, (float) ar);
            BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, aj, (float) an, ak, 0.4999f, (float) ar);
            BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, aj, 0.0f, ak, 0.4999f, aq);
            BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, al, 0.0f, am, 0.0f, aq);
            BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, al, (float) an, am, 0.0f, (float) ar);
            var as = 0.0f;
            if (entity.tickCount % 2 == 0) as = 0.5f;
            BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f,
                    Mth.cos(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + 2.3561945f) * 0.282f,
                    (float) an,
                    Mth.sin(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + 2.3561945f) * 0.282f, 0.5f,
                    as + 0.5f);
            BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f,
                    Mth.cos(entity.level().getGameTime() + partialTick * 0.05f * -1.5f + 0.7853982f) * 0.282f,
                    (float) an, aa, 1.0f, as + 0.5f);
            BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ad, (float) an, ae, 1.0f, as);
            BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ab, (float) an, ac, 0.5f, as);
            poseStack.popPose();
        }
    }

    @Override
    public void preRender(PoseStack poseStack, BFGEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        RenderUtils.faceRotation(poseStack, animatable, partialTick);
        poseStack.scale(animatable.tickCount > 2 ? 0.5F : 0.0F, animatable.tickCount > 2 ? 0.5F : 0.0F,
                animatable.tickCount > 2 ? 0.5F : 0.0F);
        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight,
                packedOverlay, red, green, blue, alpha);
    }

}