package mod.azure.doom.client.render;

import mod.azure.doom.client.models.ProwlerModel;
import mod.azure.doom.entity.tierheavy.ProwlerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ProwlerRender extends GeoEntityRenderer<ProwlerEntity> {

	public ProwlerRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new ProwlerModel());
	}

	@Override
	protected float getDeathMaxRotation(ProwlerEntity entityLivingBaseIn) {
		return 0.0F;
	}

}