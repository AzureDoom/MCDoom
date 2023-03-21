package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.ClassicBronzeModel;
import mod.azure.doom.item.armor.ClassicBronzeDoomArmor;
import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoArmorRenderer;

public class ClassicBronzeRender extends GeoArmorRenderer<ClassicBronzeDoomArmor> {
	public ClassicBronzeRender() {
		super(new ClassicBronzeModel());
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