package mod.azure.doom.client.render.armors;

import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoArmorRenderer;
import mod.azure.doom.client.models.armor.PraetorModel;
import mod.azure.doom.items.armor.PraetorDoomArmor;

public class PraetorRender extends GeoArmorRenderer<PraetorDoomArmor> {
	public PraetorRender() {
		super(new PraetorModel());
	}

	@Override
	public GeoBone getLeftBootBone() {
		return this.model.getBone("armorRightBoot").orElse(null);
	}

	@Override
	public GeoBone getLeftLegBone() {
		return this.model.getBone("armorRightLeg").orElse(null);
	}

	@Override
	public GeoBone getRightBootBone() {
		return this.model.getBone("armorLeftBoot").orElse(null);
	}

	@Override
	public GeoBone getRightLegBone() {
		return this.model.getBone("armorLeftLeg").orElse(null);
	}
}