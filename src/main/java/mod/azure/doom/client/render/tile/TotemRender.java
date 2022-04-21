package mod.azure.doom.client.render.tile;

import mod.azure.doom.client.models.tile.TotemModel;
import mod.azure.doom.entity.tileentity.TotemEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3q.renderers.geo.GeoBlockRenderer;

public class TotemRender extends GeoBlockRenderer<TotemEntity> {
	public TotemRender() {
		super(new TotemModel());
	}

	@Override
	public RenderLayer getRenderType(TotemEntity animatable, float partialTicks, MatrixStack stack,
			VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			Identifier textureLocation) {
		return RenderLayer.getEntityTranslucent(getTextureLocation(animatable));
	}

}
