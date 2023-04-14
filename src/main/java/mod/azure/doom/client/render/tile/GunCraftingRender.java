package mod.azure.doom.client.render.tile;

import org.jetbrains.annotations.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;

import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoBlockRenderer;
import mod.azure.azurelib.renderer.layer.BlockAndItemGeoLayer;
import mod.azure.doom.client.models.tile.GunCraftingModel;
import mod.azure.doom.entity.tileentity.GunBlockEntity;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.world.item.ItemStack;

public class GunCraftingRender extends GeoBlockRenderer<GunBlockEntity> {

	public GunCraftingRender() {
		super(new GunCraftingModel());
		this.addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			@Nullable
			@Override
			protected ItemStack getStackForBone(GeoBone bone, GunBlockEntity animatable) {
				return switch (bone.getName()) {
				case "gun" -> new ItemStack(DoomItems.PISTOL);
				default -> null;
				};
			}

			@Override
			protected TransformType getTransformTypeForStack(GeoBone bone, ItemStack stack, GunBlockEntity animatable) {
				return switch (bone.getName()) {
				default -> TransformType.THIRD_PERSON_RIGHT_HAND;
				};
			}

			@Override
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack, GunBlockEntity animatable, MultiBufferSource bufferSource, float partialTick, int packedLight, int packedOverlay) {
				poseStack.mulPose(Vector3f.XP.rotationDegrees(-40));
				poseStack.mulPose(Vector3f.YP.rotationDegrees(0));
				poseStack.mulPose(Vector3f.ZP.rotationDegrees(0));
				poseStack.translate(0.15D, 0.0D, 0.0D);
				poseStack.scale(0.5f, 0.5f, 0.5f);
				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight, packedOverlay);
			}
		});
	}

}