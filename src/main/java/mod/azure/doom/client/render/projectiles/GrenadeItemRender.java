package mod.azure.doom.client.render.projectiles;

import mod.azure.doom.client.models.projectiles.GrenadeItemModel;
import mod.azure.doom.item.weapons.GrenadeItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class GrenadeItemRender extends GeoItemRenderer<GrenadeItem> {

	public GrenadeItemRender() {
		super(new GrenadeItemModel());
	}

}