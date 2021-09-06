package mod.azure.doom.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.azure.doom.client.models.Pinky2016Model;
import mod.azure.doom.entity.tierheavy.Pinky2016;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class Pinky2016Render extends GeoEntityRenderer<Pinky2016> {

	public Pinky2016Render(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new Pinky2016Model());
	}

	@Override
	public RenderType getRenderType(Pinky2016 animatable, float partialTicks, MatrixStack stack,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(Pinky2016 entityLivingBaseIn) {
		return 0.0F;
	}
}