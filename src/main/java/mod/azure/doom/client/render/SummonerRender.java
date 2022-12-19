package mod.azure.doom.client.render;

import mod.azure.doom.client.models.SummonerModel;
import mod.azure.doom.entity.tiersuperheavy.SummonerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SummonerRender extends GeoEntityRenderer<SummonerEntity> {

	public SummonerRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new SummonerModel());
	}

	@Override
	protected float getDeathMaxRotation(SummonerEntity entityLivingBaseIn) {
		return 0.0F;
	}

}