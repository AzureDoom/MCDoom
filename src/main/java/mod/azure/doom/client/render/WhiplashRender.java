package mod.azure.doom.client.render;

import mod.azure.doom.client.models.WhiplashModel;
import mod.azure.doom.entity.tierheavy.WhiplashEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WhiplashRender extends GeoEntityRenderer<WhiplashEntity> {

	public WhiplashRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new WhiplashModel());
	}

	@Override
	protected float getDeathMaxRotation(WhiplashEntity entityLivingBaseIn) {
		return 0.0F;
	}

}