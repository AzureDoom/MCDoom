package mod.azure.doom.client.render;

import mod.azure.doom.client.models.CacodemonModel;
import mod.azure.doom.entity.tierheavy.CacodemonEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CacodemonRender extends GeoEntityRenderer<CacodemonEntity> {

	public CacodemonRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new CacodemonModel());
	}

	@Override
	protected float getDeathMaxRotation(CacodemonEntity entityLivingBaseIn) {
		return 0.0F;
	}

}