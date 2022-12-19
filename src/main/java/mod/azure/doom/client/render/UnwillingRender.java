package mod.azure.doom.client.render;

import mod.azure.doom.client.models.UnwillingModel;
import mod.azure.doom.entity.tierfodder.UnwillingEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class UnwillingRender extends GeoEntityRenderer<UnwillingEntity> {

	public UnwillingRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new UnwillingModel());
	}

	@Override
	protected float getDeathMaxRotation(UnwillingEntity entityLivingBaseIn) {
		return 0.0F;
	}

}