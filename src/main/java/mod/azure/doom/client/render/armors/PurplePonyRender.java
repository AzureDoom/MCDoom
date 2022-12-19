package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.PurplePonyModel;
import mod.azure.doom.item.armor.PurplePonyDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class PurplePonyRender extends GeoArmorRenderer<PurplePonyDoomArmor> {
	public PurplePonyRender() {
		super(new PurplePonyModel());
	}
}