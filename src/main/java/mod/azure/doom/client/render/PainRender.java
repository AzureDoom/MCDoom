package mod.azure.doom.client.render;

import mod.azure.doom.client.models.PainModel;
import mod.azure.doom.entity.tierheavy.PainEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PainRender extends GeoEntityRenderer<PainEntity> {

	public PainRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new PainModel());
	}

	@Override
	protected int getBlockLight(PainEntity entity, BlockPos blockPos) {
		return entity.getAttckingState() == 1 ? 15 : 1;
	}

	@Override
	protected float getDeathMaxRotation(PainEntity entityLivingBaseIn) {
		return 0.0F;
	}

}