package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.DemonicModel;
import mod.azure.doom.item.armor.DemonicDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class DemonicRender extends GeoArmorRenderer<DemonicDoomArmor> {
	public DemonicRender() {
		super(new DemonicModel());
	}
}