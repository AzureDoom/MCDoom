package mod.azure.doom.client.render.tile;

import javax.annotation.Nullable;

import mod.azure.doom.client.models.tile.GunCraftingModel;
import mod.azure.doom.entity.tileentity.GunBlockEntity;
import mod.azure.doom.util.registry.DoomItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

public class GunCraftingRender extends GeoBlockRenderer<GunBlockEntity> {

	public GunCraftingRender() {
		super(new GunCraftingModel());
		this.addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			@Nullable
			@Override
			protected ItemStack getStackForBone(GeoBone bone, GunBlockEntity animatable) {
				return switch (bone.getName()) {
				case "group" -> new ItemStack(DoomItems.PISTOL);
				default -> null;
				};
			}

			@Override
			protected ModelTransformation.Mode getTransformTypeForStack(GeoBone bone, ItemStack stack,
					GunBlockEntity animatable) {
				return switch (bone.getName()) {
				default -> ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND;
				};
			}

			@Override
			protected void renderStackForBone(MatrixStack poseStack, GeoBone bone, ItemStack stack,
					GunBlockEntity animatable, VertexConsumerProvider bufferSource, float partialTick, int packedLight,
					int packedOverlay) {
				poseStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-40));
				poseStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(0));
				poseStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-5));
				poseStack.translate(-0.10D, 0.96D, 0.9D);
				poseStack.scale(0.5f, 0.5f, 0.5f);
				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick, packedLight,
						packedOverlay);
			}
		});
	}

}