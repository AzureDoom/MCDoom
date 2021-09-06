package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.ChainsawModel;
import mod.azure.doom.item.weapons.ChainsawAnimated;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class ChainsawRender extends GeoItemRenderer<ChainsawAnimated> {

	public ChainsawRender() {
		super(new ChainsawModel());
	}
}