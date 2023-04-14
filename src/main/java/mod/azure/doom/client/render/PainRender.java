package mod.azure.doom.client.render;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.PainModel;
import mod.azure.doom.entity.tierheavy.PainEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;

public class PainRender extends GeoEntityRenderer<PainEntity> {

	public PainRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new PainModel());
	}

	@Override
	protected int getBlockLightLevel(PainEntity entity, BlockPos blockPos) {
		return entity.getAttckingState() == 1 ? 15 : 1;
	}

	@Override
	protected float getDeathMaxRotation(PainEntity entityLivingBaseIn) {
		return 0.0F;
	}

}