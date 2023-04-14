package mod.azure.doom.client.render;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.azurelib.renderer.layer.BlockAndItemGeoLayer;
import mod.azure.doom.client.models.ZombiemanModel;
import mod.azure.doom.entity.tierfodder.ZombiemanEntity;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.item.ItemStack;

public class ZombiemanRender extends GeoEntityRenderer<ZombiemanEntity> {

	public ZombiemanRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new ZombiemanModel());
		addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			@Nullable
			@Override
			protected ItemStack getStackForBone(GeoBone bone, ZombiemanEntity animatable) {
				return switch (bone.getName()) {
				case "bipedLeftArm_1" -> new ItemStack(DoomItems.PISTOL.get());
				default -> null;
				};
			}

			@Override
			protected TransformType getTransformTypeForStack(GeoBone bone, ItemStack stack, ZombiemanEntity animatable) {
				return switch (bone.getName()) {
				default -> TransformType.THIRD_PERSON_RIGHT_HAND;
				};
			}

			@Override
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, ZombiemanEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
				poseStack.mulPose(Vector3f.XP.rotationDegrees(-110));
				poseStack.mulPose(Vector3f.YP.rotationDegrees(0));
				poseStack.mulPose(Vector3f.ZP.rotationDegrees(0));
				poseStack.translate(0.0D, 0.0D, -0.4D);
				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
			}
		});
	}

	@Override
	protected float getDeathMaxRotation(ZombiemanEntity entityLivingBaseIn) {
		return 0.0F;
	}

}