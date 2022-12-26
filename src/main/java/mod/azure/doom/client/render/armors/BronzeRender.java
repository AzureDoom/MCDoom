package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.BronzeModel;
import mod.azure.doom.item.armor.BronzeDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BronzeRender extends GeoArmorRenderer<BronzeDoomArmor> {
	public BronzeRender() {
		super(new BronzeModel());
	}
}