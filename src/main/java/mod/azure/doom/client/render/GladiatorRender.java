package mod.azure.doom.client.render;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.GladiatorModel;
import mod.azure.doom.entity.tierboss.GladiatorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class GladiatorRender extends GeoEntityRenderer<GladiatorEntity> {

	public GladiatorRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new GladiatorModel());
	}

	@Override
	protected float getDeathMaxRotation(GladiatorEntity entityLivingBaseIn) {
		return 0.0F;
	}

}