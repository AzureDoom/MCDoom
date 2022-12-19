package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.HotrodModel;
import mod.azure.doom.item.armor.HotrodDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class HotrodRender extends GeoArmorRenderer<HotrodDoomArmor> {
	public HotrodRender() {
		super(new HotrodModel());
	}
}