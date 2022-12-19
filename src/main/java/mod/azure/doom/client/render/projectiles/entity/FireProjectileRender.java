package mod.azure.doom.client.render.projectiles.entity;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.FireProjectile;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class FireProjectileRender extends EntityRenderer<FireProjectile> {

	private static final Identifier ARGENT_BOLT_TEXTURE = new Identifier(DoomMod.MODID,
			"textures/entity/projectiles/argent_bolt.png");

	public FireProjectileRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public Identifier getTexture(FireProjectile entity) {
		return ARGENT_BOLT_TEXTURE;
	}

	protected int getBlockLight(FireProjectile entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public void render(FireProjectile persistentProjectileEntity, float f, float g, MatrixStack matrixStack,
			VertexConsumerProvider vertexConsumerProvider, int i) {
		matrixStack.push();
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(
				MathHelper.lerp(g, persistentProjectileEntity.prevYaw, persistentProjectileEntity.getYaw()) - 90.0F));
		matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(
				MathHelper.lerp(g, persistentProjectileEntity.prevPitch, persistentProjectileEntity.getPitch())));

		matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(45.0F));
		matrixStack.scale(0.05625F, 0.05625F, 0.05625F);
		matrixStack.translate(-4.0D, 0.0D, 0.0D);

		matrixStack.pop();
		super.render(persistentProjectileEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

}