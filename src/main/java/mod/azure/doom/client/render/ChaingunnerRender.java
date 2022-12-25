package mod.azure.doom.client.render;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import mod.azure.doom.client.models.ChaingunnerModel;
import mod.azure.doom.entity.tierfodder.ChaingunnerEntity;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class ChaingunnerRender extends GeoEntityRenderer<ChaingunnerEntity> {

	public ChaingunnerRender(EntityRendererProvider.Context renderManagerIn) {
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
			protected ItemTransforms.TransformType getTransformTypeForStack(GeoBone bone, ItemStack stack,
					ChaingunnerEntity animatable) {
				return switch (bone.getName()) {
				default -> ItemTransforms.TransformType.THIRD_PERSON_RIGHT_HAND;
				};
			}

			@Override
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack,
					ChaingunnerEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight,
					int packedOverlay) {
				poseStack.mulPose(Axis.XP.rotationDegrees(-50));
				poseStack.mulPose(Axis.YP.rotationDegrees(0));
				poseStack.mulPose(Axis.ZP.rotationDegrees(0));
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