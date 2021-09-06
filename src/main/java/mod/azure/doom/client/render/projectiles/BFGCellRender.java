package mod.azure.doom.client.render.projectiles;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.models.projectiles.BFGBallModel;
import mod.azure.doom.entity.projectiles.BFGEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class BFGCellRender extends GeoProjectilesRenderer<BFGEntity> {

	private static final RenderType BEAM = RenderType
			.entitySmoothCutout(new ResourceLocation(DoomMod.MODID, "textures/entity/projectiles/bfg_beam.png"));

	public BFGCellRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new BFGBallModel());
	}

	@Override
	public RenderType getRenderType(BFGEntity animatable, float partialTicks, MatrixStack stack,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	protected int getBlockLightLevel(BFGEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public void render(GeoModel model, BFGEntity animatable, float partialTicks, RenderType type,
			MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder,
			int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder,
				packedLightIn, packedOverlayIn, red, green, blue, alpha);
		float f = getY(animatable, partialTicks);
		LivingEntity target = animatable.getTargetedEntity();
		if (target != null) {
			float f3 = (float) target.getX();
			float f4 = (float) target.getY();
			float f5 = (float) target.getZ();
			float f6 = (float) ((double) f3 - animatable.getX());
			float f7 = (float) ((double) f4 - animatable.getY());
			float f8 = (float) ((double) f5 - animatable.getZ());
			matrixStackIn.translate((double) f6, (double) f7, (double) f8);
			renderCrystalBeams(-f6, -f7 + f, -f8, partialTicks, animatable.tickCount, matrixStackIn, renderTypeBuffer,
					packedLightIn);
		}
	}
	
	@Override
	public void renderEarly(BFGEntity animatable, MatrixStack stackIn, float ticks,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red,
				green, blue, partialTicks);
		stackIn.scale(animatable.tickCount > 2 ? 1.0F : 0.0F, animatable.tickCount > 2 ? 1.0F : 0.0F,
				animatable.tickCount > 2 ? 1.0F : 0.0F);
	}

	public static void renderCrystalBeams(float p_229059_0_, float p_229059_1_, float p_229059_2_, float p_229059_3_,
			int p_229059_4_, MatrixStack p_229059_5_, IRenderTypeBuffer p_229059_6_, int p_229059_7_) {
		float f = MathHelper.sqrt(p_229059_0_ * p_229059_0_ + p_229059_2_ * p_229059_2_);
		float f1 = MathHelper.sqrt(p_229059_0_ * p_229059_0_ + p_229059_1_ * p_229059_1_ + p_229059_2_ * p_229059_2_);
		p_229059_5_.pushPose();
		p_229059_5_.translate(0.0D, 1.0D, 0.0D);
		p_229059_5_.mulPose(Vector3f.YP
				.rotation((float) (-Math.atan2((double) p_229059_2_, (double) p_229059_0_)) - ((float) Math.PI / 2F)));
		p_229059_5_.mulPose(
				Vector3f.XP.rotation((float) (-Math.atan2((double) f, (double) p_229059_1_)) - ((float) Math.PI / 2F)));
		IVertexBuilder ivertexbuilder = p_229059_6_.getBuffer(BEAM);
		float f2 = 0.0F - ((float) p_229059_4_ + p_229059_3_) * 0.01F;
		float f3 = MathHelper.sqrt(p_229059_0_ * p_229059_0_ + p_229059_1_ * p_229059_1_ + p_229059_2_ * p_229059_2_)
				/ 32.0F - ((float) p_229059_4_ + p_229059_3_) * 0.01F;
		float f4 = 0.0F;
		float f5 = 0.75F;
		float f6 = 0.0F;
		MatrixStack.Entry matrixstack$entry = p_229059_5_.last();
		Matrix4f matrix4f = matrixstack$entry.pose();
		Matrix3f matrix3f = matrixstack$entry.normal();

		for (int j = 1; j <= 8; ++j) {
			float f7 = MathHelper.sin((float) j * ((float) Math.PI * 2F) / 8.0F) * 0.75F;
			float f8 = MathHelper.cos((float) j * ((float) Math.PI * 2F) / 8.0F) * 0.75F;
			float f9 = (float) j / 8.0F;
			ivertexbuilder.vertex(matrix4f, f4 * 0.2F, f5 * 0.2F, 0.0F).color(0, 0, 0, 255).uv(f6, f2)
					.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_229059_7_).normal(matrix3f, 0.0F, -1.0F, 0.0F)
					.endVertex();
			ivertexbuilder.vertex(matrix4f, f4, f5, f1).color(255, 255, 255, 255).uv(f6, f3)
					.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_229059_7_).normal(matrix3f, 0.0F, -1.0F, 0.0F)
					.endVertex();
			ivertexbuilder.vertex(matrix4f, f7, f8, f1).color(255, 255, 255, 255).uv(f9, f3)
					.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_229059_7_).normal(matrix3f, 0.0F, -1.0F, 0.0F)
					.endVertex();
			ivertexbuilder.vertex(matrix4f, f7 * 0.2F, f8 * 0.2F, 0.0F).color(0, 0, 0, 255).uv(f9, f2)
					.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_229059_7_).normal(matrix3f, 0.0F, -1.0F, 0.0F)
					.endVertex();
			f4 = f7;
			f5 = f8;
			f6 = f9;
		}

		p_229059_5_.popPose();
	}

	public static float getY(BFGEntity p_229051_0_, float p_229051_1_) {
		float f = (float) p_229051_0_.tickCount + p_229051_1_;
		float f1 = MathHelper.sin(f * 0.2F) / 2.0F + 0.5F;
		f1 = (f1 * f1 + f1) * 0.4F;
		return f1 - 1.4F;
	}

}