package mod.azure.doom.client.render;

import mod.azure.doom.client.models.HellknightModel;
import mod.azure.doom.entity.tierheavy.HellknightEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HellknightRender extends GeoEntityRenderer<HellknightEntity> {

	public HellknightRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new HellknightModel());
	}

	@Override
	protected float getDeathMaxRotation(HellknightEntity entityLivingBaseIn) {
		return 0.0F;
	}

}