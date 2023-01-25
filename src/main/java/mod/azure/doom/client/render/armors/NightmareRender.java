package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.NightmareModel;
import mod.azure.doom.item.armor.NightmareDoomArmor;
import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoArmorRenderer;

public class NightmareRender extends GeoArmorRenderer<NightmareDoomArmor> {
	public NightmareRender() {
		super(new NightmareModel());
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