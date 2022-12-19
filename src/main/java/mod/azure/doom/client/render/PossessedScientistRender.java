package mod.azure.doom.client.render;

import javax.annotation.Nullable;

import mod.azure.doom.client.models.PossessedScientistModel;
import mod.azure.doom.entity.tierfodder.PossessedScientistEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.RotationAxis;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class PossessedScientistRender extends GeoEntityRenderer<PossessedScientistEntity> {

	public PossessedScientistRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new PossessedScientistModel());
		this.addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			@Nullable
			@Override
			protected ItemStack getStackForBone(GeoBone bone, PossessedScientistEntity animatable) {
				return switch (bone.getName()) {
				case "Left_forearm" -> new ItemStack(Items.POTION);
				default -> null;
				};
			}

			@Override
			protected ModelTransformation.Mode getTransformTypeForStack(GeoBone bone, ItemStack stack,
					PossessedScientistEntity animatable) {
				return switch (bone.getName()) {
				default -> ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND;
				};
			}

			@Override
			protected void renderStackForBone(MatrixStack poseStack, GeoBone bone, ItemStack stack,
					PossessedScientistEntity animatable, VertexConsumerProvider bufferSource, float partialTick,
					int packedLight, int packedOverlay) {
				poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(0));
				poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-30));
				poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(0));
				poseStack.translate(0.02D, -0.8D, -0.1D);
				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight,
						packedOverlay);
			}
		});
	}

	@Override
	protected float getDeathMaxRotation(PossessedScientistEntity entityLivingBaseIn) {
		return 0.0F;
	}

}