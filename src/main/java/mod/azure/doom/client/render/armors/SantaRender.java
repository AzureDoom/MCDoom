package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.SantaModel;
import mod.azure.doom.item.armor.SantaDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SantaRender extends GeoArmorRenderer<SantaDoomArmor> {
	public SantaRender() {
		super(new SantaModel());
	}
}