package mod.azure.doom.client.render;

import mod.azure.doom.client.models.FireBaronModel;
import mod.azure.doom.entity.tiersuperheavy.FireBaronEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FireBaronRender extends GeoEntityRenderer<FireBaronEntity> {

	public FireBaronRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new FireBaronModel());
	}

	@Override
	protected float getDeathMaxRotation(FireBaronEntity entityLivingBaseIn) {
		return 0.0F;
	}

}