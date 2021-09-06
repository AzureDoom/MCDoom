package mod.azure.doom.client.render;

import mod.azure.doom.client.models.ChaingunnerModel;
import mod.azure.doom.entity.tierfodder.ChaingunnerEntity;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.json.ModelTransformation.Mode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderer.geo.GeoEntityRenderer;

public class ChaingunnerRender extends GeoEntityRenderer<ChaingunnerEntity> {

	private static final ItemStack chaingun = new ItemStack(DoomItems.CHAINGUN);
	private VertexConsumerProvider rtb;
	private Identifier whTexture;

	public ChaingunnerRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new ChaingunnerModel());
	}

	@Override
	public RenderLayer getRenderType(ChaingunnerEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderEarly(ChaingunnerEntity animatable, MatrixStack stackIn, float ticks,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		this.rtb = renderTypeBuffer;
		this.whTexture = this.getTextureLocation(animatable);
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
				red, green, blue, partialTicks);
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float alpha) {
		if (bone.getName().equals("bipedLeftArm_1")) {
			stack.push();
			stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(0));
			stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
			stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(0));
			stack.translate(0.4D, 1.04D, -0.32D);
			stack.scale(1.0f, 1.0f, 1.0f);
			MinecraftClient.getInstance().getItemRenderer().renderItem(chaingun, Mode.THIRD_PERSON_RIGHT_HAND,
					packedLightIn, packedOverlayIn, stack, this.rtb);
			stack.pop();
			bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
		}
		super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(ChaingunnerEntity entityLivingBaseIn) {
		return 0.0F;
	}

}