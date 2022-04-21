package mod.azure.doom.client.render.projectiles;

import mod.azure.doom.DoomMod;
import mod.azure.doom.client.models.projectiles.MeatHookEntityModel;
import mod.azure.doom.entity.projectiles.MeatHookEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3q.renderers.geo.GeoProjectilesRenderer;

public class MeatHookEntityRenderer extends GeoProjectilesRenderer<MeatHookEntity> {

	private static final Identifier CHAIN_TEXTURE = new Identifier(DoomMod.MODID, "textures/entity/chain.png");
	private static final RenderLayer CHAIN_LAYER = RenderLayer.getEntitySmoothCutout(CHAIN_TEXTURE);

	public MeatHookEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new MeatHookEntityModel());
	}

	protected int getBlockLight(MeatHookEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(MeatHookEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void render(MeatHookEntity hookshot, float yaw, float tickDelta, MatrixStack stack,
			VertexConsumerProvider provider, int light) {
		super.render(hookshot, yaw, tickDelta, stack, provider, light);
		if (hookshot.getOwner()instanceof PlayerEntity player) {
			@SuppressWarnings("resource")
			Arm mainArm = MinecraftClient.getInstance().options.mainArm;
			Hand activeHand = player.getActiveHand();

			stack.push();
			boolean rightHandIsActive = (mainArm == Arm.RIGHT && activeHand == Hand.MAIN_HAND)
					|| (mainArm == Arm.LEFT && activeHand == Hand.OFF_HAND);
			double bodyYawToRads = Math.toRadians(player.bodyYaw);
			double radius = rightHandIsActive ? -0.4D : 0.9D;
			double startX = player.getX() + radius * Math.cos(bodyYawToRads);
			double startY = player.getY() + (player.getHeight() / 3D);
			double startZ = player.getZ() + radius * Math.sin(bodyYawToRads);
			float distanceX = (float) (startX - hookshot.getX());
			float distanceY = (float) (startY - hookshot.getY());
			float distanceZ = (float) (startZ - hookshot.getZ());

			renderChain(distanceX, distanceY, distanceZ, tickDelta, hookshot.age, stack, provider, light);
			stack.pop();
		}
	}

	public void renderChain(float x, float y, float z, float tickDelta, int age, MatrixStack stack,
			VertexConsumerProvider provider, int light) {
		float lengthXY = MathHelper.sqrt(x * x + z * z);
		float squaredLength = x * x + y * y + z * z;
		float length = MathHelper.sqrt(squaredLength);

		stack.push();
		stack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion((float) (-Math.atan2(z, x)) - 1.5707964F));
		stack.multiply(Vec3f.POSITIVE_X.getRadialQuaternion((float) (-Math.atan2(lengthXY, y)) - 1.5707964F));
		stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(25));
		stack.push();
		stack.translate(0.015, -0.2, 0);

		VertexConsumer vertexConsumer = provider.getBuffer(CHAIN_LAYER);
		float vertX1 = 0F;
		float vertY1 = 0.25F;
		float vertX2 = MathHelper.sin(6.2831855F) * 0.125F;
		float vertY2 = MathHelper.cos(6.2831855F) * 0.125F;
		float minU = 0F;
		float maxU = 0.1875F;
		float minV = 0.0F - ((float) age + tickDelta) * 0.01F;
		float maxV = MathHelper.sqrt(squaredLength) / 8F - ((float) age + tickDelta) * 0.01F;
		MatrixStack.Entry entry = stack.peek();
		Matrix4f matrix4f = entry.getModel();
		Matrix3f matrix3f = entry.getNormal();

		vertexConsumer.vertex(matrix4f, vertX1, vertY1, 0F).color(0, 0, 0, 255).texture(minU, minV)
				.overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
		vertexConsumer.vertex(matrix4f, vertX1, vertY1, length).color(255, 255, 255, 255).texture(minU, maxV)
				.overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
		vertexConsumer.vertex(matrix4f, vertX2, vertY2, length).color(255, 255, 255, 255).texture(maxU, maxV)
				.overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
		vertexConsumer.vertex(matrix4f, vertX2, vertY2, 0F).color(0, 0, 0, 255).texture(maxU, minV)
				.overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();

		stack.pop();
		stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90));
		stack.translate(-0.015, -0.2, 0);

		entry = stack.peek();
		matrix4f = entry.getModel();
		matrix3f = entry.getNormal();

		vertexConsumer.vertex(matrix4f, vertX1, vertY1, 0F).color(0, 0, 0, 255).texture(minU, minV)
				.overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
		vertexConsumer.vertex(matrix4f, vertX1, vertY1, length).color(255, 255, 255, 255).texture(minU, maxV)
				.overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
		vertexConsumer.vertex(matrix4f, vertX2, vertY2, length).color(255, 255, 255, 255).texture(maxU, maxV)
				.overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();
		vertexConsumer.vertex(matrix4f, vertX2, vertY2, 0F).color(0, 0, 0, 255).texture(maxU, minV)
				.overlay(OverlayTexture.DEFAULT_UV).light(light).normal(matrix3f, 0.0F, -1.0F, 0.0F).next();

		stack.pop();
	}
}