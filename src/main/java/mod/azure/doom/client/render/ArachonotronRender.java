package mod.azure.doom.client.render;

import mod.azure.doom.client.models.ArachnotronModel;
import mod.azure.doom.entity.tierheavy.ArachnotronEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import mod.azure.azurelib.renderer.GeoEntityRenderer;

public class ArachonotronRender extends GeoEntityRenderer<ArachnotronEntity> {

	public ArachonotronRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new ArachnotronModel());
		this.shadowRadius = 0.7F;
	}

	@Override
	protected float getDeathMaxRotation(ArachnotronEntity entityLivingBaseIn) {
		return 0.0F;
	}

}