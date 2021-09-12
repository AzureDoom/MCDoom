package mod.azure.doom.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;

import mod.azure.doom.client.models.tile.GunCraftingModel;
import mod.azure.doom.entity.tileentity.GunBlockEntity;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class GunCraftingRender extends GeoBlockRenderer<GunBlockEntity> {

	private static final ItemStack gun = new ItemStack(DoomItems.PISTOL.get());
	private MultiBufferSource rtb;
	private ResourceLocation whTexture;

	public GunCraftingRender(BlockEntityRendererProvider.Context rendererDispatcherIn) {
		super(rendererDispatcherIn, new GunCraftingModel());
	}

	@Override
	public RenderType getRenderType(GunBlockEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderEarly(GunBlockEntity animatable, PoseStack stackIn, float ticks,
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
		if (bone.getName().equals("group")) {
			stack.pushPose();
			stack.mulPose(Vector3f.XP.rotationDegrees(-40));
			stack.mulPose(Vector3f.YP.rotationDegrees(0));
			stack.mulPose(Vector3f.ZP.rotationDegrees(-5));
			stack.translate(-0.10D, 0.96D, 0.9D);
			stack.scale(0.5f, 0.5f, 0.5f);
			Minecraft.getInstance().getItemRenderer().renderStatic(gun, TransformType.THIRD_PERSON_RIGHT_HAND,
					packedLightIn, packedOverlayIn, stack, this.rtb, 0);
			stack.popPose();
			bufferIn = rtb.getBuffer(RenderType.entityTranslucent(whTexture));
		}
		super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

}