package mod.azure.doom.client.render.projectiles;

import mod.azure.doom.client.models.projectiles.ShellModel;
import mod.azure.doom.entity.projectiles.ShotgunShellEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderer.geo.GeoProjectilesRenderer;

public class ShotgunShellRender extends GeoProjectilesRenderer<ShotgunShellEntity> {

	public ShotgunShellRender(EntityRenderDispatcher renderManagerIn) {
		super(renderManagerIn, new ShellModel());
	}

	protected int getBlockLightLevel(ShotgunShellEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	public RenderLayer getRenderType(ShotgunShellEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

	@Override
	public void renderEarly(ShotgunShellEntity animatable, MatrixStack stackIn, float ticks,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float partialTicks) {
		super.renderEarly(animatable, stackIn, ticks, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn,
				red, green, blue, partialTicks);
		stackIn.scale(animatable.age > 2 ? 1F : 0.0F, animatable.age > 2 ? 1F : 0.0F,
				animatable.age > 2 ? 1F : 0.0F);
	}
}
