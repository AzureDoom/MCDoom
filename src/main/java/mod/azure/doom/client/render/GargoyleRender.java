package mod.azure.doom.client.render;

import mod.azure.doom.client.models.GargoyleModel;
import mod.azure.doom.entity.tierfodder.GargoyleEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GargoyleRender extends GeoEntityRenderer<GargoyleEntity> {

	public GargoyleRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new GargoyleModel());
	}

	@Override
	protected float getDeathMaxRotation(GargoyleEntity entityLivingBaseIn) {
		return 0.0F;
	}

}