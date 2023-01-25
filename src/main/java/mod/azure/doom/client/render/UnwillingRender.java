package mod.azure.doom.client.render;

import mod.azure.doom.client.models.UnwillingModel;
import mod.azure.doom.entity.tierfodder.UnwillingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import mod.azure.azurelib.renderer.GeoEntityRenderer;

public class UnwillingRender extends GeoEntityRenderer<UnwillingEntity> {

	public UnwillingRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new UnwillingModel());
	}

	@Override
	protected float getDeathMaxRotation(UnwillingEntity entityLivingBaseIn) {
		return 0.0F;
	}

}