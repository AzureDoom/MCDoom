package mod.azure.doom.client.render;

import mod.azure.doom.client.models.BaronModel;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import mod.azure.azurelib.renderer.GeoEntityRenderer;

public class BaronRender extends GeoEntityRenderer<BaronEntity> {

	public BaronRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new BaronModel());
	}

	@Override
	protected float getDeathMaxRotation(BaronEntity entityLivingBaseIn) {
		return 0.0F;
	}

}