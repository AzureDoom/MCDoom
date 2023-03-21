package mod.azure.doom.client.render.projectiles;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.util.RenderUtils;
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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BFGCellRender extends GeoEntityRenderer<BFGEntity> {

	private static final RenderType CRYSTAL_BEAM_LAYER = RenderType.entityTranslucent(DoomMod.modResource("textures/entity/projectiles/bfg_beam.png"));

	public BFGCellRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new BFGBallModel());
	}

	protected int getBlockLightLevel(BFGEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public boolean shouldRender(BFGEntity entity, Frustum frustum, double x, double y, double z) {
		LivingEntity livingEntity;
		if (super.shouldRender(entity, frustum, x, y, z))
			return true;
		if (entity.hasTargetedEntity() && (livingEntity = entity.getTargetedEntity()) != null) {
			var Vec3 = this.fromLerpedPosition(livingEntity, (double) livingEntity.getBbHeight() * 0.5, 1.0f);
			var Vec32 = this.fromLerpedPosition(entity, entity.getEyeHeight(), 1.0f);
			return frustum.isVisible(new AABB(Vec32.x, Vec32.y, Vec32.z, Vec3.x, Vec3.y, Vec3.z));
		}
		return false;
	}

	private Vec3 fromLerpedPosition(Entity entity, double yOffset, float delta) {
		return new Vec3(Mth.lerp((double) delta, entity.xOld, entity.getX()), Mth.lerp((double) delta, entity.yOld, entity.getY()) + yOffset, Mth.lerp((double) delta, entity.zOld, entity.getZ()));
	}

	private static void vertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, float x, float y, float z, int red, int green, int blue, float u, float v) {
		vertexConsumer.vertex(positionMatrix, x, y, z).color(255, 255, 255, 255).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0.0f, -1.0f, 0.0f).endVertex();
	}

	@Override
	public void render(BFGEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
		var livingEntity = entity.getTargetedEntity();
		if (livingEntity != null) {
			poseStack.pushPose();
			poseStack.translate(0.0f, entity.getEyeHeight(), 0.0f);
			var Vec33 = this.fromLerpedPosition(livingEntity, (double) livingEntity.getBbHeight() * 0.5, partialTick).subtract(this.fromLerpedPosition(entity, entity.getEyeHeight(), partialTick));
			Vec33 = Vec33.normalize();
			poseStack.mulPose(Axis.YP.rotationDegrees((1.5707964f - (float) Math.atan2(Vec33.z, Vec33.x)) * 57.295776f));
			poseStack.mulPose(Axis.XP.rotationDegrees((float) Math.acos(Vec33.y) * 57.295776f));
			var s = 64 + (int) (entity.getBeamProgress(partialTick) * entity.getBeamProgress(partialTick) * 191.0f);
			var t = 32 + (int) (entity.getBeamProgress(partialTick) * entity.getBeamProgress(partialTick) * 191.0f);
			var u = 128 - (int) (entity.getBeamProgress(partialTick) * entity.getBeamProgress(partialTick) * 64.0f);
			var aa = Mth.sin((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + 0.7853982f) * 0.282f;
			var ab = Mth.cos((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + 3.926991f) * 0.282f;
			var ac = Mth.sin((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + 3.926991f) * 0.282f;
			var ad = Mth.cos((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + 5.4977875f) * 0.282f;
			var ae = Mth.sin((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + 5.4977875f) * 0.282f;
			var af = Mth.cos((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + (float) Math.PI) * 0.2f;
			var ag = Mth.sin((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + (float) Math.PI) * 0.2f;
			var ah = Mth.cos((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + 0.0f) * 0.2f;
			var ai = Mth.sin((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + 0.0f) * 0.2f;
			var aj = Mth.cos((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + 1.5707964f) * 0.2f;
			var ak = Mth.sin((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + 1.5707964f) * 0.2f;
			var al = Mth.cos((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + 4.712389f) * 0.2f;
			var am = Mth.sin((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + 4.712389f) * 0.2f;
			var an = (float) (Vec33.length() + 1.0);
			var aq = -1.0f + (float) entity.level.getGameTime() + partialTick * 0.5f % 1.0f;
			var ar = (float) (Vec33.length() + 1.0) * 2.5f + aq;
			var vertexConsumer = bufferSource.getBuffer(CRYSTAL_BEAM_LAYER);
			var entry = poseStack.last();
			var matrix4f = entry.pose();
			var matrix3f = entry.normal();
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, af, an, ag, s, t, u, 0.4999f, ar);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, af, 0.0f, ag, s, t, u, 0.4999f, aq);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ah, 0.0f, ai, s, t, u, 0.0f, aq);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ah, an, ai, s, t, u, 0.0f, ar);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, aj, an, ak, s, t, u, 0.4999f, ar);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, aj, 0.0f, ak, s, t, u, 0.4999f, aq);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, al, 0.0f, am, s, t, u, 0.0f, aq);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, al, an, am, s, t, u, 0.0f, ar);
			var as = 0.0f;
			if (entity.tickCount % 2 == 0)
				as = 0.5f;
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, Mth.cos((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + 2.3561945f) * 0.282f, an, Mth.sin((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + 2.3561945f) * 0.282f, s, t, u, 0.5f, as + 0.5f);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, Mth.cos((float) entity.level.getGameTime() + partialTick * 0.05f * -1.5f + 0.7853982f) * 0.282f, an, aa, s, t, u, 1.0f, as + 0.5f);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ad, an, ae, s, t, u, 1.0f, as);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ab, an, ac, s, t, u, 0.5f, as);
			poseStack.popPose();
		}
	}

	@Override
	public void preRender(PoseStack poseStack, BFGEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);
		poseStack.scale(animatable.tickCount > 2 ? 0.5F : 0.0F, animatable.tickCount > 2 ? 0.5F : 0.0F, animatable.tickCount > 2 ? 0.5F : 0.0F);
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

}