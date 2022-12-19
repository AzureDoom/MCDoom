package mod.azure.doom.client.render;

import mod.azure.doom.client.models.CyberdemonModel;
import mod.azure.doom.entity.tiersuperheavy.CyberdemonEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CyberdemonRender extends GeoEntityRenderer<CyberdemonEntity> {

	public CyberdemonRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new CyberdemonModel());
	}

	@Override
	protected float getDeathMaxRotation(CyberdemonEntity entityLivingBaseIn) {
		return 0.0F;
	}
}