package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.HeavyCannonModel;
import mod.azure.doom.item.weapons.HeavyCannon;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class HeavyCannonRender extends GeoItemRenderer<HeavyCannon> {

	public HeavyCannonRender() {
		super(new HeavyCannonModel());
	}
}