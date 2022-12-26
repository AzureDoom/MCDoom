package mod.azure.doom.client.render;

import mod.azure.doom.client.models.PinkyModel;
import mod.azure.doom.entity.tierheavy.PinkyEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PinkyRender extends GeoEntityRenderer<PinkyEntity> {

	public PinkyRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new PinkyModel());
	}

	@Override
	protected float getDeathMaxRotation(PinkyEntity entityLivingBaseIn) {
		return 0.0F;
	}

}