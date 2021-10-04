package mod.azure.doom.client.render;

import mod.azure.doom.client.models.MarauderModel;
import mod.azure.doom.entity.tiersuperheavy.MarauderEntity;
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

public class MarauderRender extends GeoEntityRenderer<MarauderEntity> {

	private static final ItemStack axe = new ItemStack(DoomItems.AXE_OPEN);
	private static final ItemStack shotgun = new ItemStack(DoomItems.SSG);
	private VertexConsumerProvider rtb;
	private Identifier whTexture;

	public MarauderRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new MarauderModel());
	}

	@Override
	public RenderLayer getRenderType(MarauderEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderEarly(MarauderEntity animatable, MatrixStack stackIn, float ticks,
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
		if (bone.getName().equals("rLowerArm")) {
			stack.push();
			stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-80));
			stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
			stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-5));
			stack.translate(0.45D, 0.25D, 0.95D);
			stack.scale(1.0f, 1.0f, 1.0f);
			MinecraftClient.getInstance().getItemRenderer().renderItem(axe, Mode.THIRD_PERSON_RIGHT_HAND, packedLightIn,
					packedOverlayIn, stack, this.rtb);
			stack.pop();
			bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
		}
		if (bone.getName().equals("lLowerArm")) {
			stack.push();
			stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-85));
			stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0));
			stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(5));
			stack.translate(-0.45D, 0.249D, 0.95D);
			stack.scale(1.0f, 1.0f, 1.0f);
			MinecraftClient.getInstance().getItemRenderer().renderItem(shotgun, Mode.THIRD_PERSON_LEFT_HAND,
					packedLightIn, packedOverlayIn, stack, this.rtb);
			stack.pop();
			bufferIn = rtb.getBuffer(RenderLayer.getEntityTranslucent(whTexture));
		}
		super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

	@Override
	protected float getDeathMaxRotation(MarauderEntity entityLivingBaseIn) {
		return 0.0F;
	}

}