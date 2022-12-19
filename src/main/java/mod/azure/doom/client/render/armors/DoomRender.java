package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.DoomModel;
import mod.azure.doom.item.armor.DoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class DoomRender extends GeoArmorRenderer<DoomArmor> {
	public DoomRender() {
		super(new DoomModel());
	}
}