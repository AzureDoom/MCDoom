package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.DemoncideModel;
import mod.azure.doom.item.armor.DemoncideDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class DemoncideRender extends GeoArmorRenderer<DemoncideDoomArmor> {
	public DemoncideRender() {
		super(new DemoncideModel());
	}
}