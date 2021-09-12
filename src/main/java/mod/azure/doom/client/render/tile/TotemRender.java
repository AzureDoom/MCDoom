package mod.azure.doom.client.render.tile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import mod.azure.doom.client.models.tile.TotemModel;
import mod.azure.doom.entity.tileentity.TotemEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class TotemRender extends GeoBlockRenderer<TotemEntity> {
	public TotemRender(BlockEntityRendererProvider.Context rendererDispatcherIn) {
		super(rendererDispatcherIn, new TotemModel());
	}

	@Override
	public RenderType getRenderType(TotemEntity animatable, float partialTicks, PoseStack stack,
			MultiBufferSource renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn,
			ResourceLocation textureLocation) {
		return RenderType.entityTranslucent(getTextureLocation(animatable));
	}

}