package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.SentinelHammerModel;
import mod.azure.doom.item.weapons.SentinelHammerItem;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class SentinelHammerRender extends GeoItemRenderer<SentinelHammerItem> {
	public SentinelHammerRender() {
		super(new SentinelHammerModel());
	}
}