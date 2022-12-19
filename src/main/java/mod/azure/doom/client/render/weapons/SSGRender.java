package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.SSGModel;
import mod.azure.doom.item.weapons.SuperShotgun;
import mod.azure.doom.util.PlayerProperties;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SSGRender extends GeoItemRenderer<SuperShotgun> {

	public SSGRender() {
		super(new SSGModel());
	}

	@Override
	public void renderRecursively(MatrixStack poseStack, SuperShotgun animatable, GeoBone bone, RenderLayer renderType,
			VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (bone.getName().equals("hook"))
			bone.setHidden(
					!((PlayerProperties) MinecraftClient.getInstance().cameraEntity).hasMeatHook() ? false : true);
		super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender, partialTick,
				packedLight, packedOverlay, red, green, blue, alpha);
	}
}