package mod.azure.doom.client.render.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import mod.azure.azurelib.cache.object.BakedGeoModel;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.util.RenderUtils;
import mod.azure.doom.DoomMod;
import mod.azure.doom.client.models.projectiles.MeatHookEntityModel;
import mod.azure.doom.config.DoomConfig;
import mod.azure.doom.entity.projectiles.MeatHookEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class MeatHookEntityRenderer extends GeoEntityRenderer<MeatHookEntity> {

	private static final RenderType CHAIN_LAYER = RenderType.entitySmoothCutout(new ResourceLocation(DoomMod.MODID, "textures/entity/chain.png"));

	public MeatHookEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new MeatHookEntityModel());
	}

	@Override
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
		if (hookshot.getOwner()instanceof final Player player) {
			poseStack.pushPose();
			final double bodyYawToRads = Math.toRadians(player.yBodyRot);
			final double radius = DoomConfig.SERVER.enable_noncenter.get() ? 0.8D : 0.0D;
			final double startX = player.getX() + radius * Math.cos(bodyYawToRads);
			final double startY = player.getY() + player.getBbHeight() / 3D;
			final double startZ = player.getZ() + radius * Math.sin(bodyYawToRads);
			final float distanceX = (float) (startX - hookshot.getX());
			final float distanceY = (float) (startY - hookshot.getY());
			final float distanceZ = (float) (startZ - hookshot.getZ());

			renderChain(distanceX, distanceY, distanceZ, tickDelta, hookshot.tickCount, poseStack, provider, light);
			poseStack.popPose();
		}
	}

	public void renderChain(float x, float y, float z, float tickDelta, int tickCount, PoseStack poseStack, MultiBufferSource provider, int light) {
		final float lengthXY = Mth.sqrt(x * x + z * z);
		final float squaredLength = x * x + y * y + z * z;
		final float length = Mth.sqrt(squaredLength);

		poseStack.pushPose();
		poseStack.mulPose(Vector3f.YP.rotation((float) -Math.atan2(z, x) - 1.5707964F));
		poseStack.mulPose(Vector3f.XP.rotation((float) -Math.atan2(lengthXY, y) - 1.5707964F));
		poseStack.mulPose(Vector3f.ZP.rotation(25));
		poseStack.pushPose();
		poseStack.translate(0.015, -0.2, 0);

		final VertexConsumer vertexConsumer = provider.getBuffer(CHAIN_LAYER);
		final float vertX1 = 0F;
		final float vertY1 = 0.25F;
		final float vertX2 = Mth.sin(6.2831855F) * 0.125F;
		final float vertY2 = Mth.cos(6.2831855F) * 0.125F;
		final float minU = 0F;
		final float maxU = 0.1875F;
		final float minV = 0.0F - (tickCount + tickDelta) * 0.01F;
		final float maxV = Mth.sqrt(squaredLength) / 8F - (tickCount + tickDelta) * 0.01F;
		PoseStack.Pose entry = poseStack.last();
		Matrix4f matrix4f = entry.pose();
		Matrix3f matrix3f = entry.normal();

		vertexConsumer.vertex(matrix4f, vertX1, vertY1, 0F).color(0, 0, 0, 255).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
		vertexConsumer.vertex(matrix4f, vertX1, vertY1, length).color(255, 255, 255, 255).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
		vertexConsumer.vertex(matrix4f, vertX2, vertY2, length).color(255, 255, 255, 255).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
		vertexConsumer.vertex(matrix4f, vertX2, vertY2, 0F).color(0, 0, 0, 255).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();

		poseStack.popPose();
		poseStack.mulPose(Vector3f.ZP.rotation(90));
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