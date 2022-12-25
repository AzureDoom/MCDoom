package mod.azure.doom.client.render;

import mod.azure.doom.client.models.TurretModel;
import mod.azure.doom.entity.tierambient.TurretEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TurretRender extends GeoEntityRenderer<TurretEntity> {

	public TurretRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new TurretModel());
	}

	@Override
	protected float getDeathMaxRotation(TurretEntity entityLivingBaseIn) {
		return 0.0F;
	}

}