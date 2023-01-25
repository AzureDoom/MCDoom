package mod.azure.doom.client.render;

import mod.azure.doom.client.models.PossessedSoldierModel;
import mod.azure.doom.entity.tierfodder.PossessedSoldierEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import mod.azure.azurelib.renderer.GeoEntityRenderer;

public class PossessedSoldierRender extends GeoEntityRenderer<PossessedSoldierEntity> {

	public PossessedSoldierRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new PossessedSoldierModel());
	}

	@Override
	protected float getDeathMaxRotation(PossessedSoldierEntity entityLivingBaseIn) {
		return 0.0F;
	}
}