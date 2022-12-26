package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.EmberModel;
import mod.azure.doom.item.armor.EmberDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class EmberRender extends GeoArmorRenderer<EmberDoomArmor> {
	public EmberRender() {
		super(new EmberModel());
	}
}