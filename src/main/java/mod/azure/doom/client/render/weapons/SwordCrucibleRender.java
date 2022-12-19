package mod.azure.doom.client.render.weapons;

import mod.azure.doom.client.models.weapons.SwordCrucibleModel;
import mod.azure.doom.item.weapons.SwordCrucibleItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class SwordCrucibleRender extends GeoItemRenderer<SwordCrucibleItem> {
	public SwordCrucibleRender() {
		super(new SwordCrucibleModel());
	}
}