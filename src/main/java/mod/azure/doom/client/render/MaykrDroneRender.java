package mod.azure.doom.client.render;

import mod.azure.doom.client.models.MaykrDroneModel;
import mod.azure.doom.entity.tierfodder.MaykrDroneEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MaykrDroneRender extends GeoEntityRenderer<MaykrDroneEntity> {

	public MaykrDroneRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new MaykrDroneModel());
	}

	protected float getLyingAngle(MaykrDroneEntity entityLivingBaseIn) {
		return 0.0F;
	}

}