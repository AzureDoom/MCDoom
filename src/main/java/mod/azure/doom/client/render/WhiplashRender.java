package mod.azure.doom.client.render;

import mod.azure.azurelib.renderer.GeoEntityRenderer;
import mod.azure.doom.client.models.WhiplashModel;
import mod.azure.doom.entity.tierheavy.WhiplashEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class WhiplashRender extends GeoEntityRenderer<WhiplashEntity> {

	public WhiplashRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new WhiplashModel());
	}

	@Override
	protected float getDeathMaxRotation(WhiplashEntity entityLivingBaseIn) {
		return 0.0F;
	}

}