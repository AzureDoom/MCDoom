package mod.azure.doom.client.render.projectiles;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.models.projectiles.BFGBallModel;
import mod.azure.doom.entity.projectiles.BFGEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.util.RenderUtils;

public class BFGCellRender extends GeoEntityRenderer<BFGEntity> {

	private static final RenderType CRYSTAL_BEAM_LAYER = RenderType
			.entityTranslucent(new ResourceLocation(DoomMod.MODID, "textures/entity/projectiles/bfg_beam.png"));

	public BFGCellRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new BFGBallModel());
	}

	protected int getBlockLightLevel(BFGEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public boolean shouldRender(BFGEntity entity, Frustum frustum, double x, double y, double z) {
		LivingEntity livingEntity;
		if (super.shouldRender(entity, frustum, x, y, z)) {
			return true;
		}
		if (entity.hasTargetedEntity() && (livingEntity = entity.getTargetedEntity()) != null) {
			Vec3 Vec3 = this.fromLerpedPosition(livingEntity, (double) livingEntity.getBbHeight() * 0.5, 1.0f);
			Vec3 Vec32 = this.fromLerpedPosition(entity, entity.getEyeHeight(), 1.0f);
			return frustum.isVisible(new AABB(Vec32.x, Vec32.y, Vec32.z, Vec3.x, Vec3.y, Vec3.z));
		}
		return false;
	}

	private Vec3 fromLerpedPosition(Entity entity, double yOffset, float delta) {
		double d = Mth.lerp((double) delta, entity.xOld, entity.getX());
		double e = Mth.lerp((double) delta, entity.yOld, entity.getY()) + yOffset;
		double f = Mth.lerp((double) delta, entity.zOld, entity.getZ());
		return new Vec3(d, e, f);
	}

	private static void vertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, float x,
			float y, float z, int red, int green, int blue, float u, float v) {
		vertexConsumer.vertex(positionMatrix, x, y, z).color(255, 255, 255, 255).uv(u, v)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT)
				.normal(normalMatrix, 0.0f, -1.0f, 0.0f).endVertex();
	}

	@Override
	public void render(BFGEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
			MultiBufferSource bufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
		LivingEntity livingEntity = entity.getTargetedEntity();
		if (livingEntity != null) {
			float h = entity.getBeamProgress(partialTick);
			float j = (float) entity.level.getGameTime() + partialTick;
			float k = j * 0.5f % 1.0f;
			float l = entity.getEyeHeight();
			poseStack.pushPose();
			poseStack.translate(0.0f, l, 0.0f);
			Vec3 Vec3 = this.fromLerpedPosition(livingEntity, (double) livingEntity.getBbHeight() * 0.5, partialTick);
			Vec3 Vec32 = this.fromLerpedPosition(entity, l, partialTick);
			Vec3 Vec33 = Vec3.subtract(Vec32);
			float m = (float) (Vec33.length() + 1.0);
			Vec33 = Vec33.normalize();
			float n = (float) Math.acos(Vec33.y);
			float o = (float) Math.atan2(Vec33.z, Vec33.x);
			poseStack.mulPose(Axis.YP.rotationDegrees((1.5707964f - o) * 57.295776f));
			poseStack.mulPose(Axis.XP.rotationDegrees(n * 57.295776f));
			float q = j * 0.05f * -1.5f;
			float r = h * h;
			int s = 64 + (int) (r * 191.0f);
			int t = 32 + (int) (r * 191.0f);
			int u = 128 - (int) (r * 64.0f);
			float x = Mth.cos(q + 2.3561945f) * 0.282f;
			float y = Mth.sin(q + 2.3561945f) * 0.282f;
			float z = Mth.cos(q + 0.7853982f) * 0.282f;
			float aa = Mth.sin(q + 0.7853982f) * 0.282f;
			float ab = Mth.cos(q + 3.926991f) * 0.282f;
			float ac = Mth.sin(q + 3.926991f) * 0.282f;
			float ad = Mth.cos(q + 5.4977875f) * 0.282f;
			float ae = Mth.sin(q + 5.4977875f) * 0.282f;
			float af = Mth.cos(q + (float) Math.PI) * 0.2f;
			float ag = Mth.sin(q + (float) Math.PI) * 0.2f;
			float ah = Mth.cos(q + 0.0f) * 0.2f;
			float ai = Mth.sin(q + 0.0f) * 0.2f;
			float aj = Mth.cos(q + 1.5707964f) * 0.2f;
			float ak = Mth.sin(q + 1.5707964f) * 0.2f;
			float al = Mth.cos(q + 4.712389f) * 0.2f;
			float am = Mth.sin(q + 4.712389f) * 0.2f;
			float an = m;
			float aq = -1.0f + k;
			float ar = m * 2.5f + aq;
			VertexConsumer vertexConsumer = bufferSource.getBuffer(CRYSTAL_BEAM_LAYER);
			PoseStack.Pose entry = poseStack.last();
			Matrix4f matrix4f = entry.pose();
			Matrix3f matrix3f = entry.normal();
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, af, an, ag, s, t, u, 0.4999f, ar);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, af, 0.0f, ag, s, t, u, 0.4999f, aq);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ah, 0.0f, ai, s, t, u, 0.0f, aq);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ah, an, ai, s, t, u, 0.0f, ar);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, aj, an, ak, s, t, u, 0.4999f, ar);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, aj, 0.0f, ak, s, t, u, 0.4999f, aq);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, al, 0.0f, am, s, t, u, 0.0f, aq);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, al, an, am, s, t, u, 0.0f, ar);
			float as = 0.0f;
			if (entity.tickCount % 2 == 0) {
				as = 0.5f;
			}
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, x, an, y, s, t, u, 0.5f, as + 0.5f);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, z, an, aa, s, t, u, 1.0f, as + 0.5f);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ad, an, ae, s, t, u, 1.0f, as);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ab, an, ac, s, t, u, 0.5f, as);
			poseStack.popPose();
		}
	}

	@Override
	public void preRender(PoseStack poseStack, BFGEntity animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);
		poseStack.scale(animatable.tickCount > 2 ? 0.5F : 0.0F, animatable.tickCount > 2 ? 0.5F : 0.0F,
				animatable.tickCount > 2 ? 0.5F : 0.0F);
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight,
				packedOverlay, red, green, blue, alpha);
	}

}