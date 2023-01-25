package mod.azure.doom.client.render;

import mod.azure.doom.client.models.MarauderModel;
import mod.azure.doom.entity.tiersuperheavy.MarauderEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import mod.azure.azurelib.renderer.GeoEntityRenderer;

public class MarauderRender extends GeoEntityRenderer<MarauderEntity> {

	public MarauderRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new MarauderModel());
	}

	@Override
	protected float getDeathMaxRotation(MarauderEntity entityLivingBaseIn) {
		return 0.0F;
	}
}