package mod.azure.doom.client.render.armors;

import mod.azure.azurelib.renderer.GeoArmorRenderer;
import mod.azure.doom.client.models.armor.DarkLordModel;
import mod.azure.doom.item.armor.DarkLordArmor;

public class DarkLordArmorRender extends GeoArmorRenderer<DarkLordArmor> {
	public DarkLordArmorRender() {
		super(new DarkLordModel());
	}
}