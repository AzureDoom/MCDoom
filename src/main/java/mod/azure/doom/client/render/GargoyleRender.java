package mod.azure.doom.client.render;

import mod.azure.doom.client.models.GargoyleModel;
import mod.azure.doom.entity.tierfodder.GargoyleEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import mod.azure.azurelib.renderer.GeoEntityRenderer;

public class GargoyleRender extends GeoEntityRenderer<GargoyleEntity> {

	public GargoyleRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new GargoyleModel());
	}

	@Override
	protected float getDeathMaxRotation(GargoyleEntity entityLivingBaseIn) {
		return 0.0F;
	}

}