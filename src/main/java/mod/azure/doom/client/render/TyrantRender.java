package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.TyrantModel;
import mod.azure.doom.entity.tiersuperheavy.Cyberdemon2016Entity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TyrantRender extends GeoEntityRenderer<Cyberdemon2016Entity> {

	public TyrantRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new TyrantModel());
	}

	@Override
	public RenderType getRenderType(Cyberdemon2016Entity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(Cyberdemon2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

}