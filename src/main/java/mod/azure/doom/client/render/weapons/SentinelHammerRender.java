package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.SentinelHammerModel;
import mod.azure.doom.item.weapons.SentinelHammerItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class SentinelHammerRender extends GeoItemRenderer<SentinelHammerItem> {
	public SentinelHammerRender() {
		super(new SentinelHammerModel());
	}
}