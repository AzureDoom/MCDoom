package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.DoomicornModel;
import mod.azure.doom.item.armor.DoomicornDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class DoomicornRender extends GeoArmorRenderer<DoomicornDoomArmor> {
	public DoomicornRender() {
		super(new DoomicornModel());
	}
}