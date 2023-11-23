package mod.azure.doom.client.render.weapons;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.doom.client.models.weapons.SSGModel;
import mod.azure.doom.helper.PlayerProperties;
import mod.azure.doom.items.weapons.SuperShotgun;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

public class SSGRender extends GeoItemRenderer<SuperShotgun> {

	public SSGRender() {
		super(new SSGModel());
	}

	@Override
	public void renderRecursively(PoseStack poseStack, SuperShotgun animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, com.mojang.blaze3d.vertex.VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (bone.getName().equals("hook"))
			bone.setHidden(!((PlayerProperties) Minecraft.getInstance().cameraEntity).hasMeatHook() ? false : true);
		super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}
}