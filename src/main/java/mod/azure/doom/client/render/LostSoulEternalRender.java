package mod.azure.doom.client.render;

import mod.azure.doom.client.models.LostSoulEternalModel;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import mod.azure.azurelib.renderer.GeoEntityRenderer;

public class LostSoulEternalRender extends GeoEntityRenderer<LostSoulEntity> {

	public LostSoulEternalRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new LostSoulEternalModel());
	}

	@Override
	protected int getBlockLightLevel(LostSoulEntity entity, BlockPos blockPos) {
		return 15;
	}

	@Override
	protected float getDeathMaxRotation(LostSoulEntity entityLivingBaseIn) {
		return 0.0F;
	}
}