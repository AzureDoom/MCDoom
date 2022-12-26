package mod.azure.doom.client.render;

import mod.azure.doom.client.models.MancubusModel;
import mod.azure.doom.entity.tierheavy.MancubusEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MancubusRender extends GeoEntityRenderer<MancubusEntity> {

	public MancubusRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new MancubusModel());
	}

	@Override
	protected float getDeathMaxRotation(MancubusEntity entityLivingBaseIn) {
		return 0.0F;
	}

}