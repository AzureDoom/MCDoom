package mod.azure.doom.client.render;

import mod.azure.doom.client.models.ImpStoneModel;
import mod.azure.doom.entity.tierfodder.ImpStoneEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ImpStoneRender extends GeoEntityRenderer<ImpStoneEntity> {

	public ImpStoneRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new ImpStoneModel());
	}

	@Override
	protected float getDeathMaxRotation(ImpStoneEntity entityLivingBaseIn) {
		return 0.0F;
	}

}