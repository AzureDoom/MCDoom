package mod.azure.doom.client.render.weapons;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.weapons.SSGModel;
import mod.azure.doom.item.weapons.SuperShotgun;
import mod.azure.doom.util.PlayerProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class SSGRender extends GeoItemRenderer<SuperShotgun> {
	private TransformType currentTransform;

	public SSGRender() {
		super(new SSGModel());
	}

	@Override
	public void renderByItem(ItemStack itemStack, TransformType p_239207_2_, PoseStack matrixStack,
			MultiBufferSource bufferIn, int combinedLightIn, int p_239207_6_) {
		currentTransform = p_239207_2_;
		super.renderByItem(itemStack, p_239207_2_, matrixStack, bufferIn, combinedLightIn, p_239207_6_);
	}

	@Override
	public Integer getUniqueID(SuperShotgun animatable) {
		if (currentTransform == TransformType.GUI) {
			return -1;
		}
		return super.getUniqueID(animatable);
	}

	@SuppressWarnings("resource")
	@Override
	public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float alpha) {
		if (bone.getName().equals("hook")) {
			bone.setHidden(!((PlayerProperties) Minecraft.getInstance().cameraEntity).hasMeatHook() ? false : true);
		}
		super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}
}