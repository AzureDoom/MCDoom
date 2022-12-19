package mod.azure.doom.client.render;

import mod.azure.doom.client.models.LostSoulModel;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LostSoulRender extends GeoEntityRenderer<LostSoulEntity> {

	public LostSoulRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new LostSoulModel());
	}

	@Override
	protected int getBlockLight(LostSoulEntity entity, BlockPos blockPos) {
		return 15;
	}

	@Override
	protected float getDeathMaxRotation(LostSoulEntity entityLivingBaseIn) {
		return 0.0F;
	}
}