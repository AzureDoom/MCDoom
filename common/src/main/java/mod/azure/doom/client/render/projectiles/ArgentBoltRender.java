package mod.azure.doom.client.render.projectiles;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.doom.MCDoom;
import mod.azure.doom.entities.projectiles.ArgentBoltEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class ArgentBoltRender extends ArrowRenderer<ArgentBoltEntity> {

	private static final ResourceLocation ARGENT_BOLT_TEXTURE = MCDoom.modResource("textures/entity/projectiles/argent_bolt.png");

	public ArgentBoltRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public ResourceLocation getTextureLocation(ArgentBoltEntity entity) {
		return ARGENT_BOLT_TEXTURE;
	}

	@Override
	public void render(ArgentBoltEntity persistentProjectileEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
		super.render(persistentProjectileEntity, f, g, matrixStack, vertexConsumerProvider, i);
		matrixStack.pushPose();
		matrixStack.scale(0, 0, 0);
		matrixStack.popPose();
	}

}