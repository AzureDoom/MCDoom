package mod.azure.doom.client.render.projectiles.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.DroneBoltEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DroneBoltRender extends EntityRenderer<DroneBoltEntity> {

	private static final ResourceLocation ARGENT_BOLT_TEXTURE = new ResourceLocation(DoomMod.MODID, "textures/entity/projectiles/argent_bolt.png");

	public DroneBoltRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public ResourceLocation getTextureLocation(DroneBoltEntity entity) {
		return ARGENT_BOLT_TEXTURE;
	}

	@Override
	protected int getBlockLightLevel(DroneBoltEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public void render(DroneBoltEntity persistentProjectileEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
		matrixStack.pushPose();
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(g, persistentProjectileEntity.yRotO, persistentProjectileEntity.getYRot()) - 90.0F));
		matrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(g, persistentProjectileEntity.xRotO, persistentProjectileEntity.getXRot())));

		matrixStack.mulPose(Vector3f.XP.rotationDegrees(45.0F));
		matrixStack.scale(0.05625F, 0.05625F, 0.05625F);
		matrixStack.translate(-4.0D, 0.0D, 0.0D);
		final VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.entityCutout(getTextureLocation(persistentProjectileEntity)));
		final PoseStack.Pose entry = matrixStack.last();
		final Matrix4f matrix4f = entry.pose();
		final Matrix3f matrix3f = entry.normal();
		vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, i);
		vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, i);
		vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, i);
		vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, i);
		vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, i);
		vertex(matrix4f, matrix3f, vertexConsumer, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, i);
		vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, i);
		vertex(matrix4f, matrix3f, vertexConsumer, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, i);

		for (int u = 0; u < 4; ++u) {
			matrixStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
			vertex(matrix4f, matrix3f, vertexConsumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, i);
			vertex(matrix4f, matrix3f, vertexConsumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, i);
			vertex(matrix4f, matrix3f, vertexConsumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, i);
			vertex(matrix4f, matrix3f, vertexConsumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, i);
		}

		matrixStack.popPose();
		super.render(persistentProjectileEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	public void vertex(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer vertexConsumer, int i, int j, int k, float f, float g, int l, int m, int n, int o) {
		vertexConsumer.vertex(matrix4f, i, j, k).color(255, 255, 255, 255).uv(f, g).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(o).normal(matrix3f, l, n, m).endVertex();
	}

}