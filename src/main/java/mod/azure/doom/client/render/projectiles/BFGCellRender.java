package mod.azure.doom.client.render.projectiles;

import org.joml.Matrix3f;
import org.joml.Matrix4f;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.models.projectiles.BFGBallModel;
import mod.azure.doom.entity.projectiles.BFGEntity;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;

public class BFGCellRender extends GeoEntityRenderer<BFGEntity> {

	private static final RenderLayer CRYSTAL_BEAM_LAYER = RenderLayer
			.getEntityTranslucent(new Identifier(DoomMod.MODID, "textures/entity/projectiles/bfg_beam.png"));

	public BFGCellRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new BFGBallModel());
	}

	protected int getBlockLight(BFGEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public boolean shouldRender(BFGEntity entity, Frustum frustum, double x, double y, double z) {
		LivingEntity livingEntity;
		if (super.shouldRender(entity, frustum, x, y, z)) {
			return true;
		}
		if (entity.hasBeamTarget() && (livingEntity = entity.getBeamTarget()) != null) {
			Vec3d vec3d = this.fromLerpedPosition(livingEntity, (double) livingEntity.getHeight() * 0.5, 1.0f);
			Vec3d vec3d2 = this.fromLerpedPosition(entity, entity.getStandingEyeHeight(), 1.0f);
			return frustum.isVisible(new Box(vec3d2.x, vec3d2.y, vec3d2.z, vec3d.x, vec3d.y, vec3d.z));
		}
		return false;
	}

	private Vec3d fromLerpedPosition(Entity entity, double yOffset, float delta) {
		double d = MathHelper.lerp((double) delta, entity.lastRenderX, entity.getX());
		double e = MathHelper.lerp((double) delta, entity.lastRenderY, entity.getY()) + yOffset;
		double f = MathHelper.lerp((double) delta, entity.lastRenderZ, entity.getZ());
		return new Vec3d(d, e, f);
	}

	private static void vertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, float x,
			float y, float z, int red, int green, int blue, float u, float v) {
		vertexConsumer.vertex(positionMatrix, x, y, z).color(255, 255, 255, 255).texture(u, v)
				.overlay(OverlayTexture.DEFAULT_UV).light(LightmapTextureManager.MAX_LIGHT_COORDINATE)
				.normal(normalMatrix, 0.0f, -1.0f, 0.0f).next();
	}

	@Override
	public void render(BFGEntity entity, float entityYaw, float partialTick, MatrixStack poseStack,
			VertexConsumerProvider bufferSource, int packedLight) {
		super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
		LivingEntity livingEntity = entity.getBeamTarget();
		if (livingEntity != null) {
			float h = entity.getBeamProgress(partialTick);
			float j = (float) entity.world.getTime() + partialTick;
			float k = j * 0.5f % 1.0f;
			float l = entity.getStandingEyeHeight();
			poseStack.push();
			poseStack.translate(0.0f, l, 0.0f);
			Vec3d vec3d = this.fromLerpedPosition(livingEntity, (double) livingEntity.getHeight() * 0.5, partialTick);
			Vec3d vec3d2 = this.fromLerpedPosition(entity, l, partialTick);
			Vec3d vec3d3 = vec3d.subtract(vec3d2);
			float m = (float) (vec3d3.length() + 1.0);
			vec3d3 = vec3d3.normalize();
			float n = (float) Math.acos(vec3d3.y);
			float o = (float) Math.atan2(vec3d3.z, vec3d3.x);
			poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((1.5707964f - o) * 57.295776f));
			poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(n * 57.295776f));
			float q = j * 0.05f * -1.5f;
			float r = h * h;
			int s = 64 + (int) (r * 191.0f);
			int t = 32 + (int) (r * 191.0f);
			int u = 128 - (int) (r * 64.0f);
			float x = MathHelper.cos(q + 2.3561945f) * 0.282f;
			float y = MathHelper.sin(q + 2.3561945f) * 0.282f;
			float z = MathHelper.cos(q + 0.7853982f) * 0.282f;
			float aa = MathHelper.sin(q + 0.7853982f) * 0.282f;
			float ab = MathHelper.cos(q + 3.926991f) * 0.282f;
			float ac = MathHelper.sin(q + 3.926991f) * 0.282f;
			float ad = MathHelper.cos(q + 5.4977875f) * 0.282f;
			float ae = MathHelper.sin(q + 5.4977875f) * 0.282f;
			float af = MathHelper.cos(q + (float) Math.PI) * 0.2f;
			float ag = MathHelper.sin(q + (float) Math.PI) * 0.2f;
			float ah = MathHelper.cos(q + 0.0f) * 0.2f;
			float ai = MathHelper.sin(q + 0.0f) * 0.2f;
			float aj = MathHelper.cos(q + 1.5707964f) * 0.2f;
			float ak = MathHelper.sin(q + 1.5707964f) * 0.2f;
			float al = MathHelper.cos(q + 4.712389f) * 0.2f;
			float am = MathHelper.sin(q + 4.712389f) * 0.2f;
			float an = m;
			float aq = -1.0f + k;
			float ar = m * 2.5f + aq;
			VertexConsumer vertexConsumer = bufferSource.getBuffer(CRYSTAL_BEAM_LAYER);
			MatrixStack.Entry entry = poseStack.peek();
			Matrix4f matrix4f = entry.getPositionMatrix();
			Matrix3f matrix3f = entry.getNormalMatrix();
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, af, an, ag, s, t, u, 0.4999f, ar);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, af, 0.0f, ag, s, t, u, 0.4999f, aq);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ah, 0.0f, ai, s, t, u, 0.0f, aq);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ah, an, ai, s, t, u, 0.0f, ar);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, aj, an, ak, s, t, u, 0.4999f, ar);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, aj, 0.0f, ak, s, t, u, 0.4999f, aq);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, al, 0.0f, am, s, t, u, 0.0f, aq);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, al, an, am, s, t, u, 0.0f, ar);
			float as = 0.0f;
			if (entity.age % 2 == 0) {
				as = 0.5f;
			}
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, x, an, y, s, t, u, 0.5f, as + 0.5f);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, z, an, aa, s, t, u, 1.0f, as + 0.5f);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ad, an, ae, s, t, u, 1.0f, as);
			BFGCellRender.vertex(vertexConsumer, matrix4f, matrix3f, ab, an, ac, s, t, u, 0.5f, as);
			poseStack.pop();
		}
	}

	@Override
	public void preRender(MatrixStack poseStack, BFGEntity animatable, BakedGeoModel model,
			VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);
		poseStack.scale(animatable.age > 2 ? 0.5F : 0.0F, animatable.age > 2 ? 0.5F : 0.0F,
				animatable.age > 2 ? 0.5F : 0.0F);
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight,
				packedOverlay, red, green, blue, alpha);
	}

}