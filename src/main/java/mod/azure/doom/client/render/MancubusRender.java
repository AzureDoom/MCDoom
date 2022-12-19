package mod.azure.doom.client.render;

import mod.azure.doom.client.models.MancubusModel;
import mod.azure.doom.entity.tierheavy.MancubusEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MancubusRender extends GeoEntityRenderer<MancubusEntity> {

	public MancubusRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new MancubusModel());
	}

	@Override
	protected float getDeathMaxRotation(MancubusEntity entityLivingBaseIn) {
		return 0.0F;
	}

}