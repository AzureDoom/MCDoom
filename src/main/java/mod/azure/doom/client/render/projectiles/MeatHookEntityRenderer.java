package mod.azure.doom.client.render.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.models.projectiles.MeatHookEntityModel;
import mod.azure.doom.entity.projectiles.MeatHookEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class MeatHookEntityRenderer extends GeoProjectilesRenderer<MeatHookEntity> {

	private static final ResourceLocation CHAIN_TEXTURE = new ResourceLocation(DoomMod.MODID,
			"textures/entity/chain.png");
	private static final RenderType CHAIN_LAYER = RenderType.entitySmoothCutout(CHAIN_TEXTURE);

	public MeatHookEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new MeatHookEntityModel());
	}

	protected int getBlockLight(MeatHookEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderType getRenderType(MeatHookEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void render(MeatHookEntity hookshot, float yaw, float tickDelta, PoseStack stack, MultiBufferSource provider,
			int light) {
		super.render(hookshot, yaw, tickDelta, stack, provider, light);
		if (hookshot.getOwner()instanceof Player player) {
			@SuppressWarnings("resource")
			HumanoidArm mainArm = Minecraft.getInstance().options.mainHand;
			InteractionHand activeHand = player.getUsedItemHand();

			stack.pushPose();
			boolean rightHandIsActive = (mainArm == HumanoidArm.RIGHT && activeHand == InteractionHand.MAIN_HAND)
					|| (mainArm == HumanoidArm.LEFT && activeHand == InteractionHand.OFF_HAND);
			double bodyYawToRads = Math.toRadians(player.yBodyRot);
			double radius = rightHandIsActive ? -0.4D : 0.4D;
			double startX = player.getX() + radius * Math.cos(bodyYawToRads);
			double startY = player.getY() + (player.getBbHeight() / 3D);
			double startZ = player.getZ() + radius * Math.sin(bodyYawToRads);
			float distanceX = (float) (startX - hookshot.getX());
			float distanceY = (float) (startY - hookshot.getY());
			float distanceZ = (float) (startZ - hookshot.getZ());

			renderChain(distanceX, distanceY, distanceZ, tickDelta, hookshot.tickCount, stack, provider, light);
			stack.popPose();
		}
	}

	public void renderChain(float x, float y, float z, float tickDelta, int age, PoseStack stack,
			MultiBufferSource provider, int light) {
		float lengthXY = Mth.sqrt(x * x + z * z);
		float squaredLength = x * x + y * y + z * z;
		float length = Mth.sqrt(squaredLength);

		stack.pushPose();
		stack.mulPose(Vector3f.YP.rotation((float) (-Math.atan2(z, x)) - 1.5707964F));
		stack.mulPose(Vector3f.XP.rotation((float) (-Math.atan2(lengthXY, y)) - 1.5707964F));
		stack.mulPose(Vector3f.ZP.rotation(25));
		stack.pushPose();
		stack.translate(0.015, -0.2, 0);

		VertexConsumer vertexConsumer = provider.getBuffer(CHAIN_LAYER);
		float vertX1 = 0F;
		float vertY1 = 0.25F;
		float vertX2 = Mth.sin(6.2831855F) * 0.125F;
		float vertY2 = Mth.cos(6.2831855F) * 0.125F;
		float minU = 0F;
		float maxU = 0.1875F;
		float minV = 0.0F - ((float) age + tickDelta) * 0.01F;
		float maxV = Mth.sqrt(squaredLength) / 8F - ((float) age + tickDelta) * 0.01F;
		PoseStack.Pose entry = stack.last();
		Matrix4f matrix4f = entry.pose();
		Matrix3f matrix3f = entry.normal();

		vertexConsumer.vertex(matrix4f, vertX1, vertY1, 0F).color(0, 0, 0, 255).uv(minU, minV)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
		vertexConsumer.vertex(matrix4f, vertX1, vertY1, length).color(255, 255, 255, 255).uv(minU, maxV)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
		vertexConsumer.vertex(matrix4f, vertX2, vertY2, length).color(255, 255, 255, 255).uv(maxU, maxV)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
		vertexConsumer.vertex(matrix4f, vertX2, vertY2, 0F).color(0, 0, 0, 255).uv(maxU, minV)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();

		stack.popPose();
		stack.mulPose(Vector3f.ZP.rotation(90));
		stack.translate(-0.015, -0.2, 0);

		entry = stack.last();
		matrix4f = entry.pose();
		matrix3f = entry.normal();

		vertexConsumer.vertex(matrix4f, vertX1, vertY1, 0F).color(0, 0, 0, 255).uv(minU, minV)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
		vertexConsumer.vertex(matrix4f, vertX1, vertY1, length).color(255, 255, 255, 255).uv(minU, maxV)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
		vertexConsumer.vertex(matrix4f, vertX2, vertY2, length).color(255, 255, 255, 255).uv(maxU, maxV)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
		vertexConsumer.vertex(matrix4f, vertX2, vertY2, 0F).color(0, 0, 0, 255).uv(maxU, minV)
				.overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();

		stack.popPose();
	}
}