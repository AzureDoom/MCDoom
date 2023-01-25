package mod.azure.doom.client.render;

import mod.azure.doom.client.models.BloodMaykrModel;
import mod.azure.doom.entity.tierheavy.BloodMaykrEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import mod.azure.azurelib.renderer.GeoEntityRenderer;

public class BloodMaykrRender extends GeoEntityRenderer<BloodMaykrEntity> {

	public BloodMaykrRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new BloodMaykrModel());
	}

	@Override
	protected float getDeathMaxRotation(BloodMaykrEntity entityLivingBaseIn) {
		return 0.0F;
	}

}