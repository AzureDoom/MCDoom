package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.NightmareModel;
import mod.azure.doom.item.armor.NightmareDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class NightmareRender extends GeoArmorRenderer<NightmareDoomArmor> {
	public NightmareRender() {
		super(new NightmareModel());
	}
}