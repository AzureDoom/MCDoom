package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.DarkLordCrucibleModel;
import mod.azure.doom.item.weapons.DarkLordCrucibleItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class DarkLordCrucibleRender extends GeoItemRenderer<DarkLordCrucibleItem> {
	public DarkLordCrucibleRender() {
		super(new DarkLordCrucibleModel());
	}
}