package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.DarkLordModel;
import mod.azure.doom.item.armor.DarkLordArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class DarkLordArmorRender extends GeoArmorRenderer<DarkLordArmor> {
	public DarkLordArmorRender() {
		super(new DarkLordModel());
	}
}