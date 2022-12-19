package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.CrimsonModel;
import mod.azure.doom.item.armor.CrimsonDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class CrimsonRender extends GeoArmorRenderer<CrimsonDoomArmor> {
	public CrimsonRender() {
		super(new CrimsonModel());
	}
}