package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.PurplePonyModel;
import mod.azure.doom.item.armor.PurplePonyDoomArmor;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class PurplePonyRender extends GeoArmorRenderer<PurplePonyDoomArmor> {
	public PurplePonyRender() {
		super(new PurplePonyModel());
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