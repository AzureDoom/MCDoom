package mod.azure.doom.client.render;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.RevenantModel;
import mod.azure.doom.entity.tierheavy.RevenantEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class RevenantRender extends GeoEntityRenderer<RevenantEntity> {

	public RevenantRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new RevenantModel());
	}

	@Override
	protected float getDeathMaxRotation(RevenantEntity entityLivingBaseIn) {
		return 0.0F;
	}

}