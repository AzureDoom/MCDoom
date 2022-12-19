package mod.azure.doom.client.render;

import mod.azure.doom.client.models.BloodMaykrModel;
import mod.azure.doom.entity.tierheavy.BloodMaykrEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BloodMaykrRender extends GeoEntityRenderer<BloodMaykrEntity> {

	public BloodMaykrRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new BloodMaykrModel());
	}

	@Override
	protected float getDeathMaxRotation(BloodMaykrEntity entityLivingBaseIn) {
		return 0.0F;
	}

}