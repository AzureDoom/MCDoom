package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.DemoncideModel;
import mod.azure.doom.item.armor.DemoncideDoomArmor;
import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoArmorRenderer;

public class DemoncideRender extends GeoArmorRenderer<DemoncideDoomArmor> {
	public DemoncideRender() {
		super(new DemoncideModel());
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