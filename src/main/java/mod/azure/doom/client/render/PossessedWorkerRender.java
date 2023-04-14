package mod.azure.doom.client.render;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.BlockAndItemGeoLayer;
import mod.azure.doom.client.models.PossessedWorkerModel;
import mod.azure.doom.entity.tierfodder.PossessedScientistEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class PossessedWorkerRender extends GeoEntityRenderer<PossessedScientistEntity> {

	public PossessedWorkerRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new PossessedWorkerModel());
		addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			@Nullable
			@Override
			protected ItemStack getStackForBone(GeoBone bone, PossessedScientistEntity animatable) {
				return switch (bone.getName()) {
				case "Left_forearm" -> new ItemStack(Items.POTION);
				default -> null;
				};
			}

			@Override
			protected TransformType getTransformTypeForStack(GeoBone bone, ItemStack stack, PossessedScientistEntity animatable) {
				return switch (bone.getName()) {
				default -> TransformType.THIRD_PERSON_RIGHT_HAND;
				};
			}

			@Override
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, PossessedScientistEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
				poseStack.mulPose(Vector3f.XP.rotationDegrees(0));
				poseStack.mulPose(Vector3f.YP.rotationDegrees(-30));
				poseStack.mulPose(Vector3f.ZP.rotationDegrees(0));
				poseStack.translate(0.02D, -0.8D, -0.1D);
				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
			}
		});
	}

	@Override
	protected float getDeathMaxRotation(PossessedScientistEntity entityLivingBaseIn) {
		return 0.0F;
	}

}