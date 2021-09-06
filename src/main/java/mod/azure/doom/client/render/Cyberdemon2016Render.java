package mod.azure.doom.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.azure.doom.client.models.Cyberdemon2016Model;
import mod.azure.doom.entity.tiersuperheavy.Cyberdemon2016Entity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class Cyberdemon2016Render extends GeoEntityRenderer<Cyberdemon2016Entity> {

	public Cyberdemon2016Render(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new Cyberdemon2016Model());
	}

	@Override
	public RenderType getRenderType(Cyberdemon2016Entity animatable, float partialTicks, MatrixStack stack,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(Cyberdemon2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

}