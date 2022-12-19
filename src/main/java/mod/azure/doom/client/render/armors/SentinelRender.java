package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.SentinelModel;
import mod.azure.doom.item.armor.SentinelDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SentinelRender extends GeoArmorRenderer<SentinelDoomArmor> {
	public SentinelRender() {
		super(new SentinelModel());
	}
}