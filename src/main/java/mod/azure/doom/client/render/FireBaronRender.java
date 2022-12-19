package mod.azure.doom.client.render;

import mod.azure.doom.client.models.FireBaronModel;
import mod.azure.doom.entity.tiersuperheavy.FireBaronEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireBaronRender extends GeoEntityRenderer<FireBaronEntity> {

	public FireBaronRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new FireBaronModel());
	}

	@Override
	protected float getDeathMaxRotation(FireBaronEntity entityLivingBaseIn) {
		return 0.0F;
	}

}