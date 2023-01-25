package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.HeavyCannonModel;
import mod.azure.doom.item.weapons.HeavyCannon;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class HeavyCannonRender extends GeoItemRenderer<HeavyCannon> {

	public HeavyCannonRender() {
		super(new HeavyCannonModel());
	}
}