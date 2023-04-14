package mod.azure.doom.client.render;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.Baron2016Model;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class Baron2016Render extends GeoEntityRenderer<BaronEntity> {

	public Baron2016Render(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new Baron2016Model());
	}

	@Override
	protected float getDeathMaxRotation(BaronEntity entityLivingBaseIn) {
		return 0.0F;
	}

}