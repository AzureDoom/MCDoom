package mod.azure.doom.client.render;

import mod.azure.doom.client.models.DreadknightModel;
import mod.azure.doom.entity.tierheavy.Hellknight2016Entity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DreadKnightRender extends GeoEntityRenderer<Hellknight2016Entity> {

	public DreadKnightRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new DreadknightModel());
	}

	@Override
	protected float getDeathMaxRotation(Hellknight2016Entity entityLivingBaseIn) {
		return 0.0F;
	}

}