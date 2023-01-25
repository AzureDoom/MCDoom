package mod.azure.doom.client.render.weapons;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.azure.doom.client.models.weapons.SSGModel;
import mod.azure.doom.item.weapons.SuperShotgun;
import mod.azure.doom.util.PlayerProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class SSGRender extends GeoItemRenderer<SuperShotgun> {

	public SSGRender() {
		super(new SSGModel());
	}

	@Override
	public void renderRecursively(PoseStack poseStack, SuperShotgun animatable, GeoBone bone, RenderType renderType,
			MultiBufferSource bufferSource, com.mojang.blaze3d.vertex.VertexConsumer buffer, boolean isReRender,
			float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (bone.getName().equals("hook"))
			bone.setHidden(!((PlayerProperties) Minecraft.getInstance().cameraEntity).hasMeatHook() ? false : true);
		super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick,
				packedLight, packedOverlay, red, green, blue, alpha);
	}
}