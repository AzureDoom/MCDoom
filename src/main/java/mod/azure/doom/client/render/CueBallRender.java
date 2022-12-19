package mod.azure.doom.client.render;

import mod.azure.doom.client.models.CueballModel;
import mod.azure.doom.entity.tierambient.CueBallEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CueBallRender extends GeoEntityRenderer<CueBallEntity> {

	public CueBallRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new CueballModel());
	}

	@Override
	protected int getBlockLight(CueBallEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

	@Override
	protected float getDeathMaxRotation(CueBallEntity entityLivingBaseIn) {
		return 0.0F;
	}

	@Override
	public boolean hasLabel(CueBallEntity entity) {
		return false;
	}
}