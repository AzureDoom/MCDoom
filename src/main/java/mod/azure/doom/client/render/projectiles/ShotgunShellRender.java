package mod.azure.doom.client.render.projectiles;

import mod.azure.doom.client.models.projectiles.ShellModel;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;

public class ShotgunShellRender extends GeoEntityRenderer<ShotgunShellEntity> {

	public ShotgunShellRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new ShellModel());
	}

	protected int getBlockLightLevel(ShotgunShellEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public void preRender(MatrixStack poseStack, ShotgunShellEntity animatable, BakedGeoModel model,
			VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);
		poseStack.scale(animatable.age > 2 ? 0.5F : 0.0F, animatable.age > 2 ? 0.5F : 0.0F,
				animatable.age > 2 ? 0.5F : 0.0F);
		super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight,
				packedOverlay, red, green, blue, alpha);
	}
}
