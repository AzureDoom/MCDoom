package mod.azure.doom.client.render;

import mod.azure.doom.client.models.LostSoulModel;
import mod.azure.doom.entity.tierfodder.LostSoulEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LostSoulRender extends GeoEntityRenderer<LostSoulEntity> {

	public LostSoulRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new LostSoulModel());
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