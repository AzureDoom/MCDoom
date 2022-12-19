package mod.azure.doom.client.render.armors;

import mod.azure.doom.client.models.armor.MaykrModel;
import mod.azure.doom.item.armor.MaykrDoomArmor;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class MaykrRender extends GeoArmorRenderer<MaykrDoomArmor> {
	public MaykrRender() {
		super(new MaykrModel());
	}
}