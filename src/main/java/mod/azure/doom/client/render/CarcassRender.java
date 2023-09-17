package mod.azure.doom.client.render;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.CarcassModel;
import mod.azure.doom.entity.tierheavy.CarcassEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class CarcassRender extends GeoEntityRenderer<CarcassEntity> {

	public CarcassRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new CarcassModel());
		shadowRadius = 0.4F;
	}

	@Override
	protected float getDeathMaxRotation(CarcassEntity entityLivingBaseIn) {
		return 0.0F;
	}

}