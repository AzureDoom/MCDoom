package mod.azure.doom.client.render;

import mod.azure.doom.client.models.GoreNestModel;
import mod.azure.doom.entity.tierambient.GoreNestEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import mod.azure.azurelib.renderer.GeoEntityRenderer;

public class GoreNestRender extends GeoEntityRenderer<GoreNestEntity> {

	public GoreNestRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new GoreNestModel());
	}

	@Override
	protected float getDeathMaxRotation(GoreNestEntity entityLivingBaseIn) {
		return 0.0F;
	}
}