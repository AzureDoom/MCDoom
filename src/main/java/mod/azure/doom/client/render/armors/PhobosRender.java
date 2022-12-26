package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.PhobosModel;
import mod.azure.doom.item.armor.PhobosDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class PhobosRender extends GeoArmorRenderer<PhobosDoomArmor> {
	public PhobosRender() {
		super(new PhobosModel());
	}
}