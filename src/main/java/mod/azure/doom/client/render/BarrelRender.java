package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.azure.doom.DoomMod;
import mod.azure.doom.entity.projectiles.BarrelEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;

public class BarrelRender extends EntityRenderer<BarrelEntity> {

	protected static final ResourceLocation TEXTURE = new ResourceLocation(DoomMod.MODID,
			"textures/blocks/barrel_explode.png");

	public BarrelRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
		this.shadowRadius = 0.5F;
	}

	public void render(BarrelEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		matrixStackIn.pushPose();
		matrixStackIn.translate(0.0D, 0.5D, 0.0D);

		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
		matrixStackIn.translate(-0.5D, -0.5D, 0.5D);
		matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		matrixStackIn.popPose();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public ResourceLocation getTextureLocation(BarrelEntity entity) {
		return TEXTURE;
	}
}