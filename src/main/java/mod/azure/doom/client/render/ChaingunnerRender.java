package mod.azure.doom.client.render;

import javax.annotation.Nullable;

import mod.azure.doom.client.models.ChaingunnerModel;
import mod.azure.doom.entity.tierfodder.ChaingunnerEntity;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class ChaingunnerRender extends GeoEntityRenderer<ChaingunnerEntity> {

	public ChaingunnerRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new ChaingunnerModel());
		this.addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			@Nullable
			@Override
			protected ItemStack getStackForBone(GeoBone bone, ChaingunnerEntity animatable) {
				return switch (bone.getName()) {
				case "bipedLeftArm_1" -> new ItemStack(DoomItems.CHAINGUN);
				default -> null;
				};
			}

			@Override
			protected ModelTransformation.Mode getTransformTypeForStack(GeoBone bone, ItemStack stack,
					ChaingunnerEntity animatable) {
				return switch (bone.getName()) {
				default -> ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND;
				};
			}

			@Override
			protected void renderStackForBone(MatrixStack poseStack, GeoBone bone, ItemStack stack,
					ChaingunnerEntity animatable, VertexConsumerProvider bufferSource, float partialTick, int packedLight,
					int packedOverlay) {
				poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-50));
				poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));
				poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0));
				poseStack.translate(0.0D, 0.0D, -0.2D);
				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight,
						packedOverlay);
			}
		});
	}

	@Override
	protected float getDeathMaxRotation(ChaingunnerEntity entityLivingBaseIn) {
		return 0.0F;
	}

}