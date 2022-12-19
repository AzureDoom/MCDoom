package mod.azure.doom.client.render;

import mod.azure.doom.client.models.ImpModel;
import mod.azure.doom.entity.tierfodder.ImpEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ImpRender extends GeoEntityRenderer<ImpEntity> {

	public ImpRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new ImpModel());
	}

	@Override
	protected float getDeathMaxRotation(ImpEntity entityLivingBaseIn) {
		return 0.0F;
	}

}