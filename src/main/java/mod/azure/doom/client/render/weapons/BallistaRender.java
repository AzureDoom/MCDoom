package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.BallistaModel;
import mod.azure.doom.item.weapons.Ballista;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class BallistaRender extends GeoItemRenderer<Ballista> {
	public BallistaRender() {
		super(new BallistaModel());
	}
}