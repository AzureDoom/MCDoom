package mod.azure.doom.client.render;

import mod.azure.doom.client.models.TurretModel;
import mod.azure.doom.entity.tierambient.TurretEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TurretRender extends GeoEntityRenderer<TurretEntity> {

	public TurretRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new TurretModel());
	}

	@Override
	protected float getDeathMaxRotation(TurretEntity entityLivingBaseIn) {
		return 0.0F;
	}

}