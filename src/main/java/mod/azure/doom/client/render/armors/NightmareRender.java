package mod.azure.doom.client.render.armors;

import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoArmorRenderer;
import mod.azure.doom.client.models.armor.NightmareModel;
import mod.azure.doom.item.armor.NightmareDoomArmor;

public class NightmareRender extends GeoArmorRenderer<NightmareDoomArmor> {
	public NightmareRender() {
		super(new NightmareModel());
	}

	@Override
	public GeoBone getLeftBootBone() {
		return model.getBone("armorRightBoot").orElse(null);
	}

	@Override
	public GeoBone getLeftLegBone() {
		return model.getBone("armorRightLeg").orElse(null);
	}

	@Override
	public GeoBone getRightBootBone() {
		return model.getBone("armorLeftBoot").orElse(null);
	}

	@Override
	public GeoBone getRightLegBone() {
		return model.getBone("armorLeftLeg").orElse(null);
	}
}