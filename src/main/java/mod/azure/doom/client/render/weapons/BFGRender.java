package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.BFGModel;
import mod.azure.doom.item.weapons.BFG;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class BFGRender extends GeoItemRenderer<BFG> {
	public BFGRender() {
		super(new BFGModel());
	}
}