package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.MarauderModel;
import mod.azure.doom.entity.tiersuperheavy.MarauderEntity;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MarauderRender extends GeoEntityRenderer<MarauderEntity> {

	private static final ItemStack axe = new ItemStack(DoomItems.AXE_OPEN.get());
	private static final ItemStack shotgun = new ItemStack(DoomItems.SSG.get());
	private MultiBufferSource rtb;
	private ResourceLocation whTexture;

	public MarauderRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new MarauderModel());
	}

	@Override
	public RenderType getRenderType(MarauderEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderEarly(MarauderEntity animatable, PoseStack stackIn, float ticks,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn,
			float red, float green, float blue, float partialTicks) {
		this.rtb = renderTypeBuffer;
		this.whTexture = this.getTextureLocation(animatable);
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
				red, green, blue, partialTicks);
	}

	@Override
	public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float alpha) {
		if (bone.getName().equals("rLowerArm")) {
			stack.pushPose();
			stack.mulPose(Vector3f.XP.rotationDegrees(-80));
			stack.mulPose(Vector3f.YP.rotationDegrees(0));
			stack.mulPose(Vector3f.ZP.rotationDegrees(-5));
			stack.translate(0.45D, 0.25D, 0.95D);
			stack.scale(1.0f, 1.0f, 1.0f);
			Minecraft.getInstance().getItemRenderer().renderStatic(axe, TransformType.THIRD_PERSON_RIGHT_HAND,
					packedLightIn, packedOverlayIn, stack, this.rtb, 0);
			stack.popPose();
			bufferIn = rtb.getBuffer(RenderType.entityTranslucent(whTexture));
		}
		if (bone.getName().equals("lLowerArm")) {
			stack.pushPose();
			stack.mulPose(Vector3f.XP.rotationDegrees(-85));
			stack.mulPose(Vector3f.YP.rotationDegrees(0));
			stack.mulPose(Vector3f.ZP.rotationDegrees(5));
			stack.translate(-0.45D, 0.249D, 0.95D);
			stack.scale(1.0f, 1.0f, 1.0f);
			Minecraft.getInstance().getItemRenderer().renderStatic(shotgun, TransformType.THIRD_PERSON_LEFT_HAND,
					packedLightIn, packedOverlayIn, stack, this.rtb, 0);
			stack.popPose();
			bufferIn = rtb.getBuffer(RenderType.entityTranslucent(whTexture));
		}
		super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(MarauderEntity entityLivingBaseIn) {
		return 0.0F;
	}
}