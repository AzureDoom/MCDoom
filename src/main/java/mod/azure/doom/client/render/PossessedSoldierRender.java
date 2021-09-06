package mod.azure.doom.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mod.azure.doom.client.models.PossessedSoldierModel;
import mod.azure.doom.entity.tierfodder.PossessedSoldierEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PossessedSoldierRender extends GeoEntityRenderer<PossessedSoldierEntity> {

	public PossessedSoldierRender(EntityRendererManager renderManagerIn) {
		super(renderManagerIn, new PossessedSoldierModel());
	}

	@Override
	public RenderType getRenderType(PossessedSoldierEntity animatable, float partialTicks, MatrixStack stack,
			IRenderTypeBuffer renderTypeBuffer, IVertexBuilder vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

	@Override
	protected float getDeathMaxRotation(PossessedSoldierEntity entityLivingBaseIn) {
		return 0.0F;
	}
}