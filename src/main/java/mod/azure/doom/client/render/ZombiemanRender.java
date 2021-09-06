package mod.azure.doom.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.azure.doom.client.models.ZombiemanModel;
import mod.azure.doom.entity.tierfodder.ZombiemanEntity;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ZombiemanRender extends GeoEntityRenderer<ZombiemanEntity> {

	private static final ItemStack chaingun = new ItemStack(DoomItems.PISTOL.get());
	private IRenderTypeBuffer rtb;
	private ResourceLocation whTexture;

	public ZombiemanRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new ZombiemanModel());
	}

	@Override
	public RenderType getRenderType(ZombiemanEntity animatable, float partialTicks, MatrixStack stack,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderEarly(ZombiemanEntity animatable, MatrixStack stackIn, float ticks,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float partialTicks) {
		this.rtb = renderTypeBuffer;
		this.whTexture = this.getTextureLocation(animatable);
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
				red, green, blue, partialTicks);
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack stack, IVertexBuilder bufferIn, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float alpha) {
		if (bone.getName().equals("bipedLeftArm_1")) {
			stack.pushPose();
			stack.mulPose(Vector3f.XP.rotationDegrees(-40));
			stack.mulPose(Vector3f.YP.rotationDegrees(0));
			stack.mulPose(Vector3f.ZP.rotationDegrees(-5));
			stack.translate(0.30D, 0.90D, 0.3D);
			stack.scale(1.0f, 1.0f, 1.0f);
			Minecraft.getInstance().getItemRenderer().renderStatic(chaingun, TransformType.THIRD_PERSON_RIGHT_HAND,
					packedLightIn, packedOverlayIn, stack, this.rtb);
			stack.popPose();
			bufferIn = rtb.getBuffer(RenderType.entityTranslucent(whTexture));
		}
		super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(ZombiemanEntity entityLivingBaseIn) {
		return 0.0F;
	}

}