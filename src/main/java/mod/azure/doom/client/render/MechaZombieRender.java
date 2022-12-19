package mod.azure.doom.client.render;

import mod.azure.doom.client.models.MechaZombieModel;
import mod.azure.doom.entity.tierfodder.MechaZombieEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MechaZombieRender extends GeoEntityRenderer<MechaZombieEntity> {

	public MechaZombieRender(EntityRendererFactory.Context renderManagerIn) {
		super(renderManagerIn, new MechaZombieModel());
	}

	@Override
	protected float getDeathMaxRotation(MechaZombieEntity entityLivingBaseIn) {
		return 0.0F;
	}

}