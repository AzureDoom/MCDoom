package mod.azure.doom.client.render.armors;

import mod.azure.azurelib.renderer.GeoArmorRenderer;
import mod.azure.doom.client.models.armor.SantaModel;
import mod.azure.doom.items.armor.SantaDoomArmor;

public class SantaRender extends GeoArmorRenderer<SantaDoomArmor> {
	public SantaRender() {
		super(new SantaModel());
	}
}