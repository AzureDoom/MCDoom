package mod.azure.doom.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.DreadknightModel;
import mod.azure.doom.entity.tierheavy.Hellknight2016Entity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.geo.render.built.GeoCube;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DreadKnightRender extends GeoEntityRenderer<Hellknight2016Entity> {

	Hellknight2016Entity animatable;

	public DreadKnightRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new DreadknightModel());
	}

	@Override
	public RenderType getRenderType(Hellknight2016Entity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(Hellknight2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

	@Override
	public void renderRecursively(GeoBone bone, PoseStack stack, VertexConsumer bufferIn, int packedLightIn,
			int packedOverlayIn, float red, float green, float blue, float alpha) {
		if (bone.getName().equals("leftblade") || bone.getName().equals("rightblade"))
			bone.setHidden(true);
		for (GeoCube cube : bone.childCubes) {
			if (bone.getName().equals("leftblade") || bone.getName().equals("rightblade"))
				renderCube(cube, stack,
						this.rtb.getBuffer(RenderType.entityTranslucent(getTextureLocation(animatable))), packedLightIn,
						packedOverlayIn, red, green, blue, 0.3F);
		}
		super.renderRecursively(bone, stack, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
	}

}