package mod.azure.doom.client.render;

import mod.azure.doom.client.models.ImpModel;
import mod.azure.doom.entity.tierfodder.ImpEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import mod.azure.azurelib.renderer.GeoEntityRenderer;

public class ImpRender extends GeoEntityRenderer<ImpEntity> {

	public ImpRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new ImpModel());
	}

	@Override
	protected float getDeathMaxRotation(ImpEntity entityLivingBaseIn) {
		return 0.0F;
	}

}