package mod.azure.doom.client.render.projectiles.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.entity.FireProjectile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class FireProjectileRender extends EntityRenderer<FireProjectile> {

	private static final ResourceLocation ARGENT_BOLT_TEXTURE = new ResourceLocation(DoomMod.MODID, "textures/entity/projectiles/argent_bolt.png");

	public FireProjectileRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public ResourceLocation getTextureLocation(FireProjectile entity) {
		return ARGENT_BOLT_TEXTURE;
	}

	@Override
	protected int getBlockLightLevel(FireProjectile entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public void render(FireProjectile persistentProjectileEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
		matrixStack.pushPose();
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(g, persistentProjectileEntity.yRotO, persistentProjectileEntity.getYRot()) - 90.0F));
		matrixStack.mulPose(Vector3f.ZP.rotationDegrees(Mth.lerp(g, persistentProjectileEntity.xRotO, persistentProjectileEntity.getXRot())));

		matrixStack.mulPose(Vector3f.XP.rotationDegrees(45.0F));
		matrixStack.scale(0.05625F, 0.05625F, 0.05625F);
		matrixStack.translate(-4.0D, 0.0D, 0.0D);

		matrixStack.popPose();
		super.render(persistentProjectileEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

}