package mod.azure.doom.client.render;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.ImpStoneModel;
import mod.azure.doom.entity.tierfodder.ImpStoneEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ImpStoneRender extends GeoEntityRenderer<ImpStoneEntity> {

	public ImpStoneRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new ImpStoneModel());
	}

	@Override
	protected float getDeathMaxRotation(ImpStoneEntity entityLivingBaseIn) {
		return 0.0F;
	}

}