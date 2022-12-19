package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.GoldModel;
import mod.azure.doom.item.armor.GoldDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class GoldRender extends GeoArmorRenderer<GoldDoomArmor> {
	public GoldRender() {
		super(new GoldModel());
	}
}