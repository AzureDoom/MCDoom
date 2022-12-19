package mod.azure.doom.client.render;

import mod.azure.doom.client.models.BaronModel;
import mod.azure.doom.entity.tiersuperheavy.BaronEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BaronRender extends GeoEntityRenderer<BaronEntity> {

	public BaronRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new BaronModel());
	}

	@Override
	protected float getDeathMaxRotation(BaronEntity entityLivingBaseIn) {
		return 0.0F;
	}

}