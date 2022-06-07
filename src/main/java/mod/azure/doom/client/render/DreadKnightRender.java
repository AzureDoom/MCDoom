package mod.azure.doom.client.render;

import mod.azure.doom.client.models.DreadknightModel;
import mod.azure.doom.entity.tierheavy.Hellknight2016Entity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DreadKnightRender extends GeoEntityRenderer<Hellknight2016Entity> {

	Hellknight2016Entity animatable;

	public DreadKnightRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new DreadknightModel());
	}

	@Override
	public RenderLayer getRenderType(Hellknight2016Entity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureResource(animatable));
	}

	@Override
	protected float getDeathMaxRotation(Hellknight2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

	@Override
	public void renderRecursively(GeoBone bone, MatrixStack stack, VertexConsumer bufferIn, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float alpha) {
		if (bone.getName().equals("leftblade") || bone.getName().equals("rightblade"))
			bone.setHidden(true);
		for (GeoCube cube : bone.childCubes) {
			if (bone.getName().equals("leftblade") || bone.getName().equals("rightblade"))
				renderCube(cube, stack,
						this.rtb.getBuffer(RenderLayer.getEntityTranslucent(getTextureResource(animatable))),
						packedLightIn, packedOverlayIn, red, green, blue, 0.3F);
		}
		super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

}