package mod.azure.doom.client.render;

import mod.azure.doom.client.models.ArmoredBaronModel;
import mod.azure.doom.entity.tiersuperheavy.ArmoredBaronEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import mod.azure.azurelib.renderer.GeoEntityRenderer;

public class ArmoredBaronRender extends GeoEntityRenderer<ArmoredBaronEntity> {

	public ArmoredBaronRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new ArmoredBaronModel());
	}

	@Override
	protected float getDeathMaxRotation(ArmoredBaronEntity entityLivingBaseIn) {
		return 0.0F;
	}

}