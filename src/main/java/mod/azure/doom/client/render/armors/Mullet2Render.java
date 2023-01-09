package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.Mullet2Model;
import mod.azure.doom.item.armor.Mullet2DoomArmor;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class Mullet2Render extends GeoArmorRenderer<Mullet2DoomArmor> {
	public Mullet2Render() {
		super(new Mullet2Model());
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