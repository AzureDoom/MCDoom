package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.SSGModel;
import mod.azure.doom.item.weapons.SuperShotgun;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class SSGRender extends GeoItemRenderer<SuperShotgun> {
	public SSGRender() {
		super(new SSGModel());
	}
}