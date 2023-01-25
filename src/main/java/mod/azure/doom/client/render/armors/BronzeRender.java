package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.BronzeModel;
import mod.azure.doom.item.armor.BronzeDoomArmor;
import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoArmorRenderer;

public class BronzeRender extends GeoArmorRenderer<BronzeDoomArmor> {
	public BronzeRender() {
		super(new BronzeModel());
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