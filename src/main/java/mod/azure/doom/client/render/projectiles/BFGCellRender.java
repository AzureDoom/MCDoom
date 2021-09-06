package mod.azure.doom.client.render.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.models.projectiles.BFGBallModel;
import mod.azure.doom.entity.projectiles.BFGEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderer.geo.GeoProjectilesRenderer;

public class BFGCellRender extends GeoProjectilesRenderer<BFGEntity> {

	private static final RenderLayer CRYSTAL_BEAM_LAYER = RenderLayer
			.getEntityTranslucent(new Identifier(DoomMod.MODID, "textures/entity/projectiles/bfg_beam.png"));

	public BFGCellRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new BFGBallModel());
	}

	protected int getBlockLight(BFGEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(BFGEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void render(GeoModel model, BFGEntity animatable, float partialTicks, RenderLayer type,
			MatrixStack matrixStackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder,
			int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder,
				packedLightIn, packedOverlayIn, red, green, blue, alpha);
		float h = getYOffset(animatable, partialTicks);
		LivingEntity target = animatable.getBeamTarget();
		if (target != null) {
			float m = (float) target.getX();
			float n = (float) target.getY();
			float o = (float) target.getZ();
			float p = (float) ((double) m - animatable.getX());
			float q = (float) ((double) n - animatable.getY());
			float r = (float) ((double) o - animatable.getZ());
			matrixStackIn.translate((double) p, (double) q, (double) r);
			renderCrystalBeam(-p, -q + h, -r, partialTicks, animatable.age, matrixStackIn, renderTypeBuffer,
					packedLightIn);
		}
	}

	@Override
	public void renderEarly(BFGEntity animatable, MatrixStack stackIn, float ticks,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
				red, green, blue, partialTicks);
		stackIn.scale(animatable.age > 2 ? 1.0F : 0.0F, animatable.age > 2 ? 1.0F : 0.0F,
				animatable.age > 2 ? 1.0F : 0.0F);
	}

	public static float getYOffset(BFGEntity crystal, float tickDelta) {
		float f = (float) crystal.age + tickDelta;
		float g = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
		g = (g * g + g) * 0.4F;
		return g - 1.4F;
	}

	public static void renderCrystalBeam(float dx, float dy, float dz, float tickDelta, int age, MatrixStack matrices,
			VertexConsumerProvider vertexConsumers, int light) {
		float f = MathHelper.sqrt(dx * dx + dz * dz);
		float g = MathHelper.sqrt(dx * dx + dy * dy + dz * dz);
		matrices.push();
		matrices.translate(0.0D, 1.0D, 0.0D);
		matrices.multiply(
				Vec3f.POSITIVE_Y.getRadialQuaternion((float) (-Math.atan2((double) dz, (double) dx)) - 1.5707964F));
		matrices.multiply(
				Vec3f.POSITIVE_X.getRadialQuaternion((float) (-Math.atan2((double) f, (double) dy)) - 1.5707964F));
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(CRYSTAL_BEAM_LAYER);
		float h = 0.0F - ((float) age + tickDelta) * 0.01F;
		float i = MathHelper.sqrt(dx * dx + dy * dy + dz * dz) / 32.0F - ((float) age + tickDelta) * 0.01F;
		float k = 0.0F;
		float l = 0.75F;
		float m = 0.0F;
		MatrixStack.Entry entry = matrices.peek();
		Matrix4f matrix4f = entry.getModel();
		Matrix3f matrix3f = entry.getNormal();

		for (int n = 1; n <= 8; ++n) {
			float o = MathHelper.sin((float) n * 6.2831855F / 8.0F) * 0.75F;
			float p = MathHelper.cos((float) n * 6.2831855F / 8.0F) * 0.75F;
			float q = (float) n / 8.0F;
			vertexConsumer.vertex(matrix4f, k * 0.2F, l * 0.2F, 0.0F).color(0, 0, 0, 255).texture(m, h)
					.overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
			vertexConsumer.vertex(matrix4f, k, l, g).color(255, 255, 255, 255).texture(m, i)
					.overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
			vertexConsumer.vertex(matrix4f, o, p, g).color(255, 255, 255, 255).texture(q, i)
					.overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
			vertexConsumer.vertex(matrix4f, o * 0.2F, p * 0.2F, 0.0F).color(0, 0, 0, 255).texture(q, h)
					.overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
			k = o;
			l = p;
			m = q;
		}

		matrices.pop();
	}

}