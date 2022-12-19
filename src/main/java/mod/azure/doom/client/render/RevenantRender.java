package mod.azure.doom.client.render;

import mod.azure.doom.client.models.RevenantModel;
import mod.azure.doom.entity.tierheavy.RevenantEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class RevenantRender extends GeoEntityRenderer<RevenantEntity> {

	public RevenantRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new RevenantModel());
	}

	@Override
	protected float getDeathMaxRotation(RevenantEntity entityLivingBaseIn) {
		return 0.0F;
	}

}